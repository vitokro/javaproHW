package ua.kiev.prog.db.DAO;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class AbstractDAO<K, T> implements DAO<K, T> {
    final private EntityManager em;
    final private Class<T> cls;

    public AbstractDAO(EntityManager em, Class<T> cls) {
        this.em = em;
        this.cls = cls;
    }

    @Override
    public Optional<T> get(K id) {
        return Optional.ofNullable(em.find(cls, id));
    }


    @Override
    public List<T> getAll() {
        return em.createQuery("SELECT u FROM USDRate u", cls).getResultList();
    }

    @Override
    public void insert(T t) {
        em.getTransaction().begin();
        try {
            em.persist(t);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    @Override
    public void update(T t) {
        em.getTransaction().begin();
        try {
            em.merge(t);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }

    }

    @Override
    public void delete(K id) {
        final T t = em.getReference(cls, id);
        if (t == null) {
            System.out.println("Entity not found!");
            return;
        }
        em.getTransaction().begin();
        try {
            em.remove(t);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    @Override
    public float getSaleMax() {
        return (float) em.createQuery("SELECT MAX(saleRate) FROM USDRate u").getSingleResult();
    }

    @Override
    public float getPurchaseMax() {
        return (float) em.createQuery("SELECT MAX(purchaseRate) FROM USDRate u").getSingleResult();
    }

    @Override
    public float getSaleMin() {
        return (float) em.createQuery("SELECT MIN(saleRate) FROM USDRate u").getSingleResult();
    }

    @Override
    public double getPurchaseAvg() {
        return (double) em.createQuery("SELECT AVG(purchaseRate) FROM USDRate u").getSingleResult();
    }

    @Override
    public double getSaleAvg() {
        return (double) em.createQuery("SELECT AVG(saleRate) FROM USDRate u").getSingleResult();
    }

    @Override
    public float getPurchaseMin() {
        return (float) em.createQuery("SELECT MIN(purchaseRate) FROM USDRate u").getSingleResult();
    }
}
