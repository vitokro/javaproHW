package ua.kiev.prog.db.dao;

import ua.kiev.prog.db.entity.Account;
import ua.kiev.prog.db.entity.Currency;
import ua.kiev.prog.db.entity.Transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

public class TransactionDAO extends AbstractDAO<Transaction> {
    private EntityManager em;

    public TransactionDAO(EntityManager em) {
        super(em, Transaction.class);
        this.em = em;
    }

    public boolean transferMoney(Account accountFrom, Account accountTo, BigDecimal money, BigDecimal moneyTo){
        final Transaction tran = new Transaction(accountFrom, accountTo, money, new Date(System.currentTimeMillis()));
//        if (accountFrom.getCurrency() != accountTo.getCurrency())
//            moneyTo = convertMoney(accountFrom.getCurrency(), accountTo.getCurrency(), money);
        
        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            accountFrom.subtractMoney(money);
            accountTo.addMoney(moneyTo);
            em.persist(tran);

            transaction.commit();
        } catch (IllegalStateException | PersistenceException ex) {
            if (transaction.isActive())
                transaction.rollback();
            return false;
        } catch (Exception e){
            System.out.println(e);
        }
        return true;
    }


}
