package ua.kiev.prog.db.DAO;

import ua.kiev.prog.db.USDRate;

import java.sql.Connection;

public class USDRateDAO extends AbstractDAO<String, USDRate> {

    public USDRateDAO(Connection conn) {
        super(conn, USDRate.class);
    }
}
