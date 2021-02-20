package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.PrivatBankRates;
import json.PrivatBankRatesList;
import ua.kiev.prog.db.dao.DAO;
import ua.kiev.prog.db.dao.ExchangeRatesDAO;
import ua.kiev.prog.db.entity.Currency;
import ua.kiev.prog.db.entity.ExchangeRates;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
//         Создать базу данных «Банк» с таблицами «Пользователи»,
//«Транзакции», «Счета» и «Курсы валют». Счет бывает 3-х видов:
//USD, EUR, UAH. Написать запросы для пополнения счета в нужной
//валюте, перевода средств с одного счета на другой, конвертации
//валюты по курсу в рамках счетов одного пользователя. Написать
//запрос для получения суммарных средств на счету одного
//пользователя в UAH (расчет по курсу).

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
        EntityManager em = emf.createEntityManager();

        try {

            getRatesFromPB(em);
            Client bank = Client.createNewClient("Bank, ATM or terminal", em);

            Client markAnthony = Client.createNewClient("Mark Anthony", em);
            bank.transferMoneyTo(markAnthony, BigDecimal.valueOf(100), Currency.UAH);

            Client caesar = Client.createNewClient("Caesar", em);
            bank.transferMoneyTo(caesar, BigDecimal.valueOf(1000.99), Currency.UAH);

            Client brut = Client.createNewClient("Brut", em);
            bank.transferMoneyTo(markAnthony, BigDecimal.valueOf(-10), Currency.UAH);

            markAnthony.transferMoneyTo(brut, BigDecimal.valueOf(50), Currency.UAH);
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("Mark Anthony = " + markAnthony.getDebit());
            System.out.println("Brut = " + brut.getDebit());
            System.out.println("Caesar = " + caesar.getDebit());
            System.out.println("----------------------------------------------------------------------------------------");

//        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

    }

    private static void getRatesFromPB(EntityManager em) throws IOException {
        final URL obj = new URL("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5");
        final HttpURLConnection http = (HttpURLConnection) obj.openConnection();
        PrivatBankRates[] rates = null;
        try (InputStreamReader is = new InputStreamReader(http.getInputStream())) {
            Gson json = new GsonBuilder().create();
            rates = json.fromJson(is, PrivatBankRates[].class);
        } catch (Exception e){
            System.out.println(e);
        }

        for(var rate: rates){
            final Currency currency = Currency.getCurrency(rate.getCcy());
            if (currency == null)
                continue;
            final ExchangeRates exchangeRate = new ExchangeRates(currency, Currency.getCurrency(rate.getBase_ccy()),
                    rate.getBuy(), rate.getSale());
            DAO<Integer, ExchangeRates> erDAO = new ExchangeRatesDAO(em);
            erDAO.insert(exchangeRate);
        }
    }
}
