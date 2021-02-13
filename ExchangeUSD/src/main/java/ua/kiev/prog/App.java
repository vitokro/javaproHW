package ua.kiev.prog;

import ua.kiev.prog.db.DbProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class App {
    public static void main(String[] args) {
        System.out.println("==================================== USD/UAH RATES for January 2021 ==================");
        System.out.println("Downloading rates, please wait....");
        DbProperties dbProp = new DbProperties();

        try (Connection conn = DriverManager.getConnection(dbProp.getUrl(), dbProp.getUser(), dbProp.getPassword())) {
            RatesManager rm = new RatesManager(conn);
            rm.saveRatesFromWeb();
            rm.showRates();
            rm.showMinsAndMaxs();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
