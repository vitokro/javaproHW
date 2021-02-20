package ua.kiev.prog.db.dao;

import ua.kiev.prog.db.entity.Account;

import javax.persistence.EntityManager;

public class AccountDAO extends AbstractDAO<Account> {

    public AccountDAO(EntityManager em) {
        super(em, Account.class);
    }
}
