package ua.kiev.prog.db.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<K, T> {
    Optional<T> get(K id);

    List<T> getAll();

    void insert(T t);

    void update(T t);

    void delete(K id);

    float getSaleMax();

    float getPurchaseMax();

    double getPurchaseAvg();

    float getSaleMin();

    double getSaleAvg();

    float getPurchaseMin();

}
