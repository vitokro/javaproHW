import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class ContextListener implements ServletContextListener {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private ScheduledExecutorService scheduler;
    private ExecutorService ratesDownloader;

    public static EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        emf = Persistence.createEntityManagerFactory("exchangeJPA");
        em = emf.createEntityManager();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        ratesDownloader = Executors.newSingleThreadExecutor();
        ratesDownloader.submit(new USDRatesLoading(em));

        scheduler.scheduleAtFixedRate(new EmailSender(em), 0, 1, TimeUnit.DAYS);
//        scheduler.scheduleAtFixedRate(new EmailSender(em), 1, 1, TimeUnit.MINUTES); // debug mode
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        em.close();
        emf.close();
        scheduler.shutdownNow();
        ratesDownloader.shutdown();
    }


}
