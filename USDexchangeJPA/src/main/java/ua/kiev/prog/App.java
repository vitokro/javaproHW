package ua.kiev.prog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;


public class App {
    public static void main(String[] args) {
        System.out.println("==================================== USD/UAH RATES for January 2021 ==================");
        System.out.println("Downloading rates, please wait....");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("exchangeJPA");
        EntityManager em = emf.createEntityManager();

            RatesManager rm = new RatesManager(em);
        try {
            rm.saveRatesFromWeb();
            rm.showRates();
            rm.showMinsAndMaxs();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            em.close();
            emf.close();
        }


    }
}
