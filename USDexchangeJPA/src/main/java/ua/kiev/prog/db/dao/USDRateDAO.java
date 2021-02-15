package ua.kiev.prog.db.dao;

import ua.kiev.prog.db.USDRate;

import javax.persistence.EntityManager;

public class USDRateDAO extends AbstractDAO<String, USDRate> {

    public USDRateDAO(EntityManager em) {
        super(em, USDRate.class);
    }
}
