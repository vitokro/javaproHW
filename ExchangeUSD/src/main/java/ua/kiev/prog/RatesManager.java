package ua.kiev.prog;

import ua.kiev.prog.db.DAO.DAO;
import ua.kiev.prog.db.USDRate;
import ua.kiev.prog.db.DAO.USDRateDAO;
import ua.kiev.prog.json.ExchangeRate;
import ua.kiev.prog.json.Rates;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RatesManager {
    private Connection conn;
    private DAO<String, USDRate> usdRateDAO;
    private List<USDRate> ratesList;

    public RatesManager(Connection conn) {
        this.conn = conn;
        this.usdRateDAO = new USDRateDAO(conn);
    }

    public void saveRatesFromWeb(){
        try {
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Calendar cal = new GregorianCalendar();

            usdRateDAO.create();
            for (int i = 1; i < 3; i++) {
                cal.set(2021, Calendar.JANUARY, i);
                String date = formatter.format(cal.getTime());

                Rates rateForDate = Rates.getRateForDate(date);
                List<ExchangeRate> exchangeRate = rateForDate.getExchangeRate();
                for (var e : exchangeRate) {
                    if ("USD".equals(e.getCurrency())) {
                        final USDRate usdRate = new USDRate(rateForDate.getDate(), e.getSaleRate(), e.getPurchaseRate());
                        usdRateDAO.insert(usdRate);
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRates()  {
        ratesList = usdRateDAO.getAll();
        if (ratesList.size() == 0) {
            System.out.println("No data");
            return;
        }
        final Field[] filedNames = ratesList.get(0).getClass().getDeclaredFields();
        System.out.println("==================================================================================================================================================");
        for (int i = 0; i < filedNames.length; i++)
                System.out.print(filedNames[i].getName() + "\t\t");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < ratesList.size(); i++) {
            System.out.println(ratesList.get(i));
        }
        System.out.println();
        System.out.println("==================================================================================================================================================");

    }

    public void showMinsAndMaxs() {
        DoubleSummaryStatistics stats = ratesList.stream()
                .mapToDouble(USDRate::getSaleRate)
                .summaryStatistics();
        System.out.println("Max sale rate was " + stats.getMax());
        System.out.println("Min sale rate was " + stats.getMin());
        System.out.println("Average sale rate was " + stats.getAverage());
        System.out.println();

        stats = ratesList.stream()
                .mapToDouble(USDRate::getPurchaseRate)
                .summaryStatistics();
        System.out.println("Max purchase rate was " + stats.getMax());
        System.out.println("Min purchase rate was " + stats.getMin());
        System.out.println("Average purchase rate was " + stats.getAverage());

    }
}
