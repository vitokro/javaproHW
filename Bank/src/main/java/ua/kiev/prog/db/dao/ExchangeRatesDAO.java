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

    public BigDecimal convertToUAH(Currency currencyFrom, BigDecimal money) {
        final TypedQuery<BigDecimal> query = em.createQuery(
                "SELECT e.buy FROM ExchangeRates e " +
                        "WHERE e.base_currency = :base " +
                        "AND e.currency = :curr", BigDecimal.class);

        query.setParameter("base", Currency.UAH);
        query.setParameter("curr", currencyFrom);
        BigDecimal saleRate = query.getSingleResult();

        return saleRate.setScale(2, RoundingMode.CEILING).multiply(money);
    }

    public BigDecimal convertUAHto(Currency currencyTo, BigDecimal money) {
        final TypedQuery<BigDecimal> query = em.createQuery(
                "SELECT e.sale FROM ExchangeRates e " +
                        "WHERE e.base_currency = :base " +
                        "AND e.currency = :curr", BigDecimal.class);

        query.setParameter("base", Currency.UAH);
        query.setParameter("curr", currencyTo);
        BigDecimal saleRate = query.getSingleResult();

        return money.setScale(2, RoundingMode.CEILING).divide(saleRate, RoundingMode.CEILING);
    }
}
