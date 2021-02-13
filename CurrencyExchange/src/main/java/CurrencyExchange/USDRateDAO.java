package CurrencyExchange;

import CurrencyExchange.db.AbstractDAO;

import java.sql.Connection;

public class USDRateDAO extends AbstractDAO<USDRate> {

    public USDRateDAO(Connection conn) {
        super(conn, USDRate.class);
    }
}
