package db.dao;


import db.entity.USDRate;

import javax.persistence.EntityManager;

public class USDRateDAO extends AbstractDAO<Integer, USDRate> {

    public USDRateDAO(EntityManager em) {
        super(em, USDRate.class);
    }
}
