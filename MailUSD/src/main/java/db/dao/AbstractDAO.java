package db.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO<K, T> implements DAO<K, T> {
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
        List<T> resultList = null;
        try {
            resultList = em.createQuery(String.format("SELECT u FROM %s u", cls.getName()), cls).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<T> getLastN(int n) {
        List<T> resultList = null;
        try {
            final TypedQuery<T> query = em.createQuery(String.format(
                    "SELECT u FROM %s u ORDER BY u.id DESC", cls.getName()), cls);
            query.setMaxResults(n);
            resultList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
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
    public double getPurchaseAvg() {
        return (double) em.createQuery("SELECT AVG(purchaseRate) FROM USDRate u").getSingleResult();
    }

    @Override
    public double getSaleAvg() {
        return (double) em.createQuery("SELECT AVG(saleRate) FROM USDRate u").getSingleResult();
    }
}
