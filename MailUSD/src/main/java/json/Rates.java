package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class Rates implements Serializable {
    private String date;
    private String bank;
    private String baseCurrency;
    private String baseCurrencyLit;
    private List<ExchangeRate> exchangeRate;

    public Rates() {
    }

    public static Rates getRateForDate(String date) throws IOException {
        final URL obj = new URL("https://api.privatbank.ua/p24api/exchange_rates?json&date=" + date);
        final HttpURLConnection http = (HttpURLConnection) obj.openConnection();
        try (InputStream is = http.getInputStream()) {
            byte[] buf = responseBodyToArray(is);
            String strBuf = new String(buf, StandardCharsets.UTF_8);
            Gson json = new GsonBuilder().create();

            return json.fromJson(strBuf, Rates.class);
        }
    }

    private static byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;
        do {
            r = is.read(buf);
            if (r > 0)
                bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }

    public String getDate() {
        return date;
    }


    public String getBank() {
        return bank;
    }


    public String getBaseCurrency() {
        return baseCurrency;
    }


    public String getBaseCurrencyLit() {
        return baseCurrencyLit;
    }


    public List<ExchangeRate> getExchangeRate() {
        return exchangeRate;
    }


    @Override
    public String toString() {
        return "Rates{" +
                "date='" + date + '\'' +
                ", bank='" + bank + '\'' +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", baseCurrencyLit='" + baseCurrencyLit + '\'' +
                ", exchangeRateList=" + exchangeRate +
                '}';
    }
}
