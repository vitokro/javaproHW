package CurrencyExchange;

import CurrencyExchange.db.DAO;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class RatesManager {
    private Connection conn;
    private DAO<USDRate> usdRateDAO;

    public RatesManager(Connection conn) {
        this.conn = conn;
        this.usdRateDAO = new USDRateDAO(conn);
    }

    public void saveRatesFromWeb(){
        try {
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Calendar cal = new GregorianCalendar();
//            List<USDRate> usdRates = new ArrayList<>();

            usdRateDAO.create();
            for (int i = 10; i < 15; i++) {
                cal.set(2020, Calendar.DECEMBER, i);
                String date = formatter.format(cal.getTime());

                Rates rateForDate = Rates.getRateForDate(date);
                List<ExchangeRate> exchangeRate = rateForDate.getExchangeRate();
                for (var e : exchangeRate) {
                    if ("USD".equals(e.getCurrency())) {
                        usdRateDAO.insert(new USDRate(rateForDate.getDate(), e.getSaleRate(), e.getPurchaseRate()));
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRates()  {
        List<USDRate> list = usdRateDAO.getAll();
        if (list.size() == 0) {
            System.out.println("No data");
            return;
        }
        final Field[] filedNames = list.get(0).getClass().getDeclaredFields();
        System.out.println("==================================================================================================================================================");
        for (int i = 0; i < filedNames.length; i++)
            System.out.print(filedNames[i].getName() + "\t\t");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println();
        System.out.println("==================================================================================================================================================");

    }

}
