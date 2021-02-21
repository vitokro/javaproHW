package ua.kiev.prog.db.dao;

import ua.kiev.prog.db.entity.Currency;
import ua.kiev.prog.db.entity.ExchangeRates;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeRatesDAO extends AbstractDAO<ExchangeRates> {

    public ExchangeRatesDAO(EntityManager em) {
        super(em, ExchangeRates.class);
    }

    public BigDecimal getSaleRate(Currency currency) {
        final TypedQuery<BigDecimal> query = em.createQuery(
                "SELECT e.sale FROM ExchangeRates e " +
                        "WHERE e.base_currency = :base " +
                        "AND e.currency = :curr", BigDecimal.class);

        query.setParameter("base", Currency.UAH);
        query.setParameter("curr", currency);

        return query.getSingleResult();
    }

    public BigDecimal getBuyRate(Currency currency) {
        final TypedQuery<BigDecimal> query = em.createQuery(
                "SELECT e.buy FROM ExchangeRates e " +
                        "WHERE e.base_currency = :base " +
                        "AND e.currency = :curr", BigDecimal.class);

        query.setParameter("base", Currency.UAH);
        query.setParameter("curr", currency);

        return query.getSingleResult();
    }

//    public BigDecimal rateUAHtoXXX(Currency currencyTo, BigDecimal money) {
//        final TypedQuery<BigDecimal> query = em.createQuery(
//                "SELECT e.sale FROM ExchangeRates e " +
//                        "WHERE e.base_currency = :base " +
//                        "AND e.currency = :curr", BigDecimal.class);
//
//        query.setParameter("base", Currency.UAH);
//        query.setParameter("curr", currencyTo);
//        BigDecimal saleRate = query.getSingleResult();
//
//        return BigDecimal.ONE.divide(saleRate, 10, RoundingMode.CEILING);
//    }
}
