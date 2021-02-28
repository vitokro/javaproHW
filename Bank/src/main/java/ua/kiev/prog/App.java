package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.PrivatBankRates;
import ua.kiev.prog.db.dao.DAO;
import ua.kiev.prog.db.dao.ExchangeRatesDAO;
import ua.kiev.prog.db.entity.Account;
import ua.kiev.prog.db.entity.Currency;
import ua.kiev.prog.db.entity.ExchangeRates;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.NoSuchElementException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
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
            getRatesFromPB(em);
            Client bank = Client.createNewClient("WorldBank", em);
            Account bankUSD = bank.createNewAccount("USD BANK ACCOUNT", Currency.USD);
            Account bankUSD2 = bank.createNewAccount("Second USD BANK ACCOUNT", Currency.USD);
            Account bankEUR = bank.createNewAccount("Euro ACCOUNT", Currency.EUR);
            Account bankUAH = bank.createNewAccount("UAH super Account", Currency.UAH);

            bank.transferMoney(bankUAH, bankUSD, BigDecimal.valueOf(100));
            bank.transferMoney(bankUSD, bankUAH, BigDecimal.valueOf(10));
            bank.transferMoney(bankEUR, bankUAH, BigDecimal.valueOf(100));

            Client markAnthony = Client.createNewClient("Mark Anthony", em);
            bank.transferMoneyTo(markAnthony, BigDecimal.valueOf(66.66), Currency.UAH);

            Client caesar = Client.createNewClient("Caesar", em);
            bank.transferMoneyTo(caesar, BigDecimal.valueOf(1000.99), Currency.UAH);

            Client brut = Client.createNewClient("Brut", em);
            bank.transferMoneyTo(markAnthony, BigDecimal.valueOf(77), Currency.UAH);

            markAnthony.transferMoneyTo(brut, BigDecimal.valueOf(33), Currency.UAH);
            final Account markUSD = markAnthony.createNewAccount("USD Account VISA", Currency.USD);
            final Account markUAH = markAnthony.createNewAccount("UAH Account MasterCard", Currency.UAH);
            final Account markEUR = markAnthony.createNewAccount("EUR Account noname", Currency.EUR);

            markAnthony.transferMoney(markUAH, markUSD, BigDecimal.valueOf(2222));
            bank.transferMoneyTo(markAnthony, BigDecimal.valueOf(111), Currency.EUR);
            markAnthony.transferMoney(markEUR, markUAH, BigDecimal.valueOf(666.89));

            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("Bank = " + bank.getDebit());
            System.out.println("Mark Anthony = " + markAnthony.getDebit());
            System.out.println("Brut = " + brut.getDebit());
            System.out.println("Caesar = " + caesar.getDebit());
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println(markAnthony.getTransactions());

        } catch (IOException | NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

    }

    public static void getRatesFromPB(EntityManager em) throws IOException {
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
