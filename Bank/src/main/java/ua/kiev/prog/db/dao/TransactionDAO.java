package ua.kiev.prog.db.dao;

import ua.kiev.prog.db.entity.Account;
import ua.kiev.prog.db.entity.BankTransaction;
import ua.kiev.prog.db.entity.Currency;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TransactionDAO extends AbstractDAO<BankTransaction> {

    public TransactionDAO(EntityManager em) {
        super(em, BankTransaction.class);
    }

    public boolean transferMoney(Account accountFrom, Account accountTo, BigDecimal money, BigDecimal rate) {
        BigDecimal moneyTo;
        if (accountFrom.getCurrency() == Currency.UAH)
            moneyTo = money.divide(rate, 2, RoundingMode.CEILING);
        else
            moneyTo = rate.multiply(money);

        final BankTransaction tran = new BankTransaction(accountFrom, accountTo, money, moneyTo, rate);

        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            accountFrom.subtractMoney(money);
            accountFrom.addTransactionFrom(tran);
            accountTo.addMoney(moneyTo);
            accountTo.addTransactionTo(tran);
            em.persist(accountFrom);
            em.persist(accountTo);

            transaction.commit();
            System.out.println("-----------------------------------------------");
            System.out.println(tran);
            System.out.println("-----------------------------------------------");
            return true;
        } catch (IllegalStateException | PersistenceException ex) {
            if (transaction.isActive())
                transaction.rollback();
            return false;
        }
    }


}
