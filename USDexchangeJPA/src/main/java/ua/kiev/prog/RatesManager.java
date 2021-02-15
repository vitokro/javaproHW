package ua.kiev.prog;

import ua.kiev.prog.db.dao.DAO;
import ua.kiev.prog.db.dao.USDRateDAO;
import ua.kiev.prog.db.USDRate;
import ua.kiev.prog.json.ExchangeRate;
import ua.kiev.prog.json.Rates;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class RatesManager {
    private DAO<String, USDRate> usdRateDAO;
    private List<USDRate> ratesList;

    public RatesManager(EntityManager em) {
        this.usdRateDAO = new USDRateDAO(em);
    }

    public void saveRatesFromWeb() throws IOException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = new GregorianCalendar();

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


    }

    public void showRates() {
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
        System.out.println("Max sale rate was " + usdRateDAO.getSaleMax());
        System.out.println("Min sale rate was " + usdRateDAO.getSaleMin());
        System.out.println("Average sale rate was " + usdRateDAO.getSaleAvg());

        System.out.println();

        System.out.println("Max purchase rate was " + usdRateDAO.getPurchaseMax());
        System.out.println("Min purchase rate was " + usdRateDAO.getPurchaseMin());
        System.out.println("Average purchase rate was " + usdRateDAO.getPurchaseAvg());

    }
}
