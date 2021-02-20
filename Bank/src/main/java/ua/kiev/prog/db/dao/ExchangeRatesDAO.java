package ua.kiev.prog.db.dao;

import ua.kiev.prog.db.entity.ExchangeRates;

import javax.persistence.EntityManager;

public class ExchangeRatesDAO extends AbstractDAO<ExchangeRates> {

    public ExchangeRatesDAO(EntityManager em) {
        super(em, ExchangeRates.class);
    }
}
