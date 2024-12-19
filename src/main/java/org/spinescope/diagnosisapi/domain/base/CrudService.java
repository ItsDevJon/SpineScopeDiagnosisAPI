package org.spinescope.diagnosisapi.domain.base;

import java.util.Optional;

public interface CrudService<T, ID> {
    T add(T t);

    Optional<T> getById(ID id);

    boolean existsById(ID id);

    Iterable<T> getAll();

    void deleteById(ID id);

    T update(T t);
}
