import db.dao.DAO;
import db.dao.EmailDAO;
import db.dao.USDRateDAO;
import db.entity.Email;
import db.entity.USDRate;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class EmailSender implements Runnable {
    final private EntityManager em;

    public EmailSender(EntityManager em) {
        this.em = em;
    }

    @Override
    public void run() {
        List<String> emailsAddrs = getEmailAddresses();
        String text = getEmailText();
        sendEmails(emailsAddrs, text);
    }

    private List<String> getEmailAddresses() {
        DAO<Integer, Email> emailDAO = new EmailDAO(em);
        final List<Email> all = emailDAO.getAll();
        return all.stream().map(Email::getEmail).collect(Collectors.toList());
    }

    private String getEmailText() {
        DAO<Integer, USDRate> usdRateDAO = new USDRateDAO(em);
        final List<USDRate> lastN = usdRateDAO.getLastN(7);
        final OptionalDouble purchAve = lastN.stream().mapToDouble(USDRate::getPurchaseRate).average();
        final OptionalDouble saleAvg = lastN.stream().mapToDouble(USDRate::getSaleRate).average();
        StringBuilder sb = new StringBuilder();
        sb.append("USD/UAH rate for today:\n")
                .append("Purchase rate: ").append(lastN.get(0).getPurchaseRate())
                .append("\nPurchase rate: ").append(lastN.get(0).getSaleRate())
                .append(String.format("\nAverage purchase rate for last 7 days: %.2f", purchAve.orElse(0)))
                .append(String.format("\nAverage sale rate for last 7 days: %.2f", saleAvg.orElse(0)));
        return sb.toString();
    }

    private void sendEmails(List<String> emailsAddrs, String msgText) {
//        https://mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/

        if (emailsAddrs.size() == 0)
            return;
        StringJoiner sj = new StringJoiner(", ");
        emailsAddrs.forEach(sj::add);
        String addressList = sj.toString();
        System.out.println("Sending email to " + addressList);


        final String user = "";//change accordingly
        final String password = "";//change accordingly

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(addressList)
            );
            message.setSubject("USD/UAH exchange rate");
            message.setText(msgText);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
