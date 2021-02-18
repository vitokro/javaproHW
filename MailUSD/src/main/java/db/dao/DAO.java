package db.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<K, T> {
    Optional<T> get(K id);

    List<T> getAll();

    List<T> getLastN(int n);

    void insert(T t);

    void delete(K id);

    void update(T t);

    double getPurchaseAvg();

    double getSaleAvg();

}
