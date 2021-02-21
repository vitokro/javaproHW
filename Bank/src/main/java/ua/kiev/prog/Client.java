package ua.kiev.prog;

import ua.kiev.prog.db.dao.ClientDAO;
import ua.kiev.prog.db.dao.DAO;
import ua.kiev.prog.db.dao.ExchangeRatesDAO;
import ua.kiev.prog.db.dao.TransactionDAO;
import ua.kiev.prog.db.entity.Account;
import ua.kiev.prog.db.entity.BankTransaction;
import ua.kiev.prog.db.entity.ClientEntity;
import ua.kiev.prog.db.entity.Currency;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

//        final Account acc = new Account("Simple UAH account", Currency.UAH, clientEntity);
//        clientEntity.addAccount(acc);
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


    public boolean transferMoney(Account accountFrom, Account accountTo, BigDecimal money)  {
        TransactionDAO transactionDAO = new TransactionDAO(em);
        BigDecimal rate = BigDecimal.ONE;
        if (accountFrom.getCurrency() != accountTo.getCurrency())
            rate = getExchangeRate(accountFrom.getCurrency(), accountTo.getCurrency());

        return transactionDAO.transferMoney(accountFrom, accountTo, money, rate);

    }

    private BigDecimal getExchangeRate(Currency currencyFrom, Currency currencyTo) {
        final boolean isFromUAH = (currencyFrom == Currency.UAH);
        if (!isFromUAH && currencyTo != Currency.UAH)
            throw new IllegalArgumentException("Transfer may not be without UAH");
        if (isFromUAH)
            return new ExchangeRatesDAO(em).getSaleRate(currencyTo);
        else
            return new ExchangeRatesDAO(em).getBuyRate(currencyFrom);

    }

    public void transferMoneyTo(Client client, BigDecimal money, Currency currency) throws NoSuchElementException {
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
            else {
                final BigDecimal rate = new ExchangeRatesDAO(em).getBuyRate(currency);
                debit = debit.add(amount.multiply(rate));
            }
        }

        return debit;
    }

    public List<BankTransaction> getTransactions() {
        List<BankTransaction> transactions = new ArrayList<>();
        for (Account account : getAccounts()) {
            transactions.addAll(account.getTransactionsFrom());
            transactions.addAll(account.getTransactionsTo());
        }
        transactions.sort((o1, o2) -> o1.getId() - o2.getId());
        return transactions;
    }
}
