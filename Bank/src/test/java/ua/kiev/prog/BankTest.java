package ua.kiev.prog;

import org.junit.Test;
import ua.kiev.prog.db.dao.ClientDAO;
import ua.kiev.prog.db.dao.DAO;
import ua.kiev.prog.db.dao.ExchangeRatesDAO;
import ua.kiev.prog.db.entity.Account;
import ua.kiev.prog.db.entity.ClientEntity;
import ua.kiev.prog.db.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BankTest extends BaseTest {

    @Test
    public void addAccounts(){
        Client bank = Client.createNewClient("WorldBank", em);
        Account bankUSD = bank.createNewAccount("USD BANK ACCOUNT", Currency.USD);
        Account bankUSD2 = bank.createNewAccount("Second USD BANK ACCOUNT", Currency.USD);
        Account bankEUR = bank.createNewAccount("Euro ACCOUNT", Currency.EUR);
        Account bankUAH = bank.createNewAccount("UAH super Account", Currency.UAH);

        final Set<Account> set1 = Set.of(bankEUR, bankUSD2, bankUSD, bankUAH);
        assertEquals(set1, bank.getAccounts());
    }

    @Test
    public void transferMoneyOneCurrency(){
        Client bank = Client.createNewClient("WorldBank", em);
        Account bankUSD = bank.createNewAccount("USD BANK ACCOUNT", Currency.USD);
        Account bankUSD2 = bank.createNewAccount("Second USD BANK ACCOUNT", Currency.USD);

        bank.transferMoney(bankUSD2, bankUSD, BigDecimal.valueOf(100));
        assertEquals(bankUSD2.getAmount(), bankUSD.getAmount().multiply(BigDecimal.valueOf(-1)));



    }

    @Test
    public void transferMoneyTwoCurrencies(){
//        Client bank = Client.createNewClient("WorldBank", em);
//        Account bankUSD = bank.createNewAccount("USD BANK ACCOUNT", Currency.USD);
//        Account bankUSD2 = bank.createNewAccount("Second USD BANK ACCOUNT", Currency.USD);
//        Account bankEUR = bank.createNewAccount("Euro ACCOUNT", Currency.EUR);
//        Account bankUAH = bank.createNewAccount("UAH super Account", Currency.UAH);
//        Account bankUAH2 = bank.createNewAccount("UAH2 super Account", Currency.UAH);
//
//        ExchangeRatesDAO er = new ExchangeRatesDAO(em);
//
//        bank.transferMoney(bankEUR, bankUAH, BigDecimal.valueOf(10));
//        final BigDecimal buyRateEUR = er.getBuyRate(Currency.EUR);
//        final BigDecimal ua = bankUAH.getAmount().divide(buyRateEUR, 2, RoundingMode.HALF_EVEN);
//        assertEquals(BigDecimal.ZERO, bankEUR.getAmount().add(ua));
//
//        bank.transferMoney(bankUAH, bankUSD, BigDecimal.valueOf(10));
//        final BigDecimal saleRateUSD = er.getSaleRate(Currency.USD);
//        final BigDecimal usd = bankUSD.getAmount().multiply(saleRateUSD);
//        assertEquals(BigDecimal.ZERO, bankUAH.getAmount().add(usd));

    }

}
