package ua.kiev.prog;

import ua.kiev.prog.db.dao.ClientDAO;
import ua.kiev.prog.db.dao.DAO;
import ua.kiev.prog.db.dao.TransactionDAO;
import ua.kiev.prog.db.entity.Account;
import ua.kiev.prog.db.entity.ClientEntity;
import ua.kiev.prog.db.entity.Currency;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private String name;
    private int id;
    private List<Account> accounts = new ArrayList<>();
    //    private BigDecimal debit;
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
        client.accounts.add(acc);
//        client.transferMoneyTo(null, acc, new BigDecimal(100));
        return client;
    }

    public void createNewAccount(Account account) {
        accounts.add(account);
    }


    public void transferMoneyTo(Account accountFrom, Account accountTo, BigDecimal money) {
        TransactionDAO transactionDAO = new TransactionDAO(em);
        BigDecimal moneyTo = money;
        if (accountFrom.getCurrency() != accountTo.getCurrency())
            moneyTo = convertMoney(accountFrom.getCurrency(), accountTo.getCurrency(), money);

        transactionDAO.transferMoney(accountFrom, accountTo, money, moneyTo);

    }

    private BigDecimal convertMoney(Currency currencyFrom, Currency currencyTo, BigDecimal money) {
        //todo
        final TypedQuery<BigDecimal> query = em.createQuery("SELECT e.buy FROM exchange_rates e " +
                "WHERE e.base_currency = :base " +
                "AND e.currency = :curr", BigDecimal.class);
        query.setParameter("base", currencyFrom);
        query.setParameter("curr", currencyTo);
        return query.getSingleResult();
    }

    private BigDecimal convertToUAH(Currency currencyFrom, BigDecimal money) {
        return convertMoney(currencyFrom, Currency.UAH, money);
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
        transferMoneyTo(accountFrom, accountTo, money);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public BigDecimal getDebit() {
        BigDecimal debit = BigDecimal.ZERO;

        for (Account account : accounts) {
            final BigDecimal amount = account.getAmount();
            final Currency currency = account.getCurrency();
            if (currency == Currency.UAH)
                debit = debit.add(amount);
            else
                debit = debit.add(convertToUAH(currency, amount));
        }

        return debit;
    }
}
