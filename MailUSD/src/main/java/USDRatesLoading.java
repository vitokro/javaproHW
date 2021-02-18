import db.dao.DAO;
import db.dao.USDRateDAO;
import db.entity.USDRate;
import json.ExchangeRate;
import json.Rates;

import javax.persistence.EntityManager;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class USDRatesLoading implements Runnable {
    private final DAO<Integer, USDRate> usdRateDAO;

    public USDRatesLoading(EntityManager em) {
        this.usdRateDAO = new USDRateDAO(em);
    }

    public void loadRatesFromWeb() throws IOException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 9; i++) {
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
            cal.add(Calendar.DATE, -1);
        }
    }

    @Override
    public void run() {
        try {
            loadRatesFromWeb();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
