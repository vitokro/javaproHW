package ua.kiev.prog.db.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO<T> implements DAO<Integer, T> {
    final protected EntityManager em;
    final private Class<T> cls;

    public AbstractDAO(EntityManager em, Class<T> cls) {
        this.em = em;
        this.cls = cls;
    }

    @Override
    public Optional<T> get(Integer id) {
        return Optional.ofNullable(em.find(cls, id));
    }


    @Override
    public List<T> getAll() {
        List<T> resultList = null;
        try {
            resultList = em.createQuery(String.format("SELECT u FROM %s u", cls.getName()), cls).getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            e.printStackTrace();
        }
        return resultList;
    }


    @Override
    public void insert(T t) {
        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.persist(t);
            transaction.commit();
        } catch (IllegalStateException | PersistenceException ex) {
            if (transaction.isActive())
                transaction.rollback();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(T t) {
        final EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        try {
            em.merge(t);
            transaction.commit();
        } catch (IllegalStateException | PersistenceException ex) {
            if (transaction.isActive())
                transaction.rollback();
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void delete(Integer id) {
        final T t = em.getReference(cls, id);
        if (t == null) {
            System.out.println("Cannot delete. Entity " + cls.getName() + " not found!");
            return;
        }
        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.remove(t);
            transaction.commit();
        } catch (IllegalStateException | PersistenceException ex) {
            if (transaction.isActive())
                transaction.rollback();
            throw new RuntimeException(ex);
        }
    }


}
