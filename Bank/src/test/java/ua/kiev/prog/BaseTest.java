package ua.kiev.prog;

import org.junit.After;
import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.concurrent.Callable;

public abstract class BaseTest {

    private EntityManagerFactory emFactory;
    protected EntityManager em;

    @Before
    public void init() {
        emFactory = Persistence.createEntityManagerFactory("Bank");
        em = emFactory.createEntityManager();
    }

    @After
    public void finish() {
        if (em != null) em.close();
        if (emFactory != null) emFactory.close();
    }


}
