package ua.kiev.prog;

import ua.kiev.prog.db.dao.ClientDAO;
import ua.kiev.prog.db.dao.DAO;
import ua.kiev.prog.db.dao.ExchangeRatesDAO;
import ua.kiev.prog.db.dao.TransactionDAO;
import ua.kiev.prog.db.entity.Account;
import ua.kiev.prog.db.entity.ClientEntity;
import ua.kiev.prog.db.entity.Currency;
import ua.kiev.prog.db.entity.BankTransaction;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class Client {

    private String name;
    private int id;
    private EntityManager em;

    private Client(String name, EntityManager em) {
        this.name = name;
        this.em = em;
    }

    public static Client createNewClient(String name, EntityManager em) {
        DAO<Integer, ClientEntity> clientDAO = new ClientDAO(em);
        final ClientEntity clientEntity = new ClientEntity(name);

        final Account acc = new Account("Simple UAH account", Currency.UAH, clientEntity);
        clientEntity.addAccount(acc);
        clientDAO.insert(clientEntity);

        final Client client = new Client(name, em);
        client.id = clientEntity.getId();
        return client;
    }

    public Account createNewAccount(String name, Currency currency) {
        final ClientEntity clientEntity = getClientEntity();
        final Account account = new Account(name, currency, clientEntity);
        clientEntity.addAccount(account);

        return account;
    }

    private ClientEntity getClientEntity() {
        DAO<Integer, ClientEntity> clientDAO = new ClientDAO(em);
        return clientDAO.get(id).orElseThrow();
    }


    public void transferMoney(Account accountFrom, Account accountTo, BigDecimal money) {
        TransactionDAO transactionDAO = new TransactionDAO(em);
        BigDecimal moneyTo = money;
        if (accountFrom.getCurrency() != accountTo.getCurrency())
            moneyTo = convertMoney(accountFrom.getCurrency(), accountTo.getCurrency(), money);

        transactionDAO.transferMoney(accountFrom, accountTo, money, moneyTo);

    }

    private BigDecimal convertMoney(Currency currencyFrom, Currency currencyTo, BigDecimal money) {
        if (currencyFrom != Currency.UAH && currencyTo != Currency.UAH )
            throw new IllegalArgumentException("Transfer may be either from UAH or to UAH");

        ExchangeRatesDAO exDAO = new ExchangeRatesDAO(em);
        if (currencyFrom == Currency.UAH) {
            return exDAO.convertUAHto(currencyTo, money);
        } else
            return exDAO.convertToUAH(currencyFrom, money);
    }

    public void transferMoneyTo(Client client, BigDecimal money, Currency currency) {
        final Account accountFrom = this.getAccounts().stream()
                .filter(account -> account.getCurrency() == currency)
                .findFirst()
                .orElseThrow();

        final Account accountTo = client.getAccounts().stream()
                .filter(account -> account.getCurrency() == currency)
                .findFirst()
                .orElseThrow();
        transferMoney(accountFrom, accountTo, money);
    }

    public Set<Account> getAccounts() {
        return getClientEntity().getAccounts();
    }

    public BigDecimal getDebit() {
        BigDecimal debit = BigDecimal.ZERO;

        for (Account account : getAccounts()) {
            final BigDecimal amount = account.getAmount();
            final Currency currency = account.getCurrency();
            if (currency == Currency.UAH)
                debit = debit.add(amount);
            else
                debit = debit.add(new ExchangeRatesDAO(em).convertToUAH(currency, amount));
        }

        return debit;
    }

    public List<BankTransaction> getTransactions(){
        return null;
    }
}
