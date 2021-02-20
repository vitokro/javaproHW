package ua.kiev.prog.db.dao;

import ua.kiev.prog.db.entity.Account;
import ua.kiev.prog.db.entity.BankTransaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

public class TransactionDAO extends AbstractDAO<BankTransaction> {

    public TransactionDAO(EntityManager em) {
        super(em, BankTransaction.class);
    }

    public boolean transferMoney(Account accountFrom, Account accountTo, BigDecimal money, BigDecimal moneyTo){
        final BankTransaction tran = new BankTransaction(accountFrom, accountTo, money, new Date(System.currentTimeMillis()));

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
