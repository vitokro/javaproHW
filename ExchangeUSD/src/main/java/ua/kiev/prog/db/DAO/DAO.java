package ua.kiev.prog.db.DAO;

import java.util.List;
import java.util.Optional;

public interface DAO<K, T> {
    Optional<T> get(K id);

    List<T> getByCondition(String condition);

    List<T> getAll();

    void insert(T t);

    void update(T t);

    void delete(K id);

    void create();

}
