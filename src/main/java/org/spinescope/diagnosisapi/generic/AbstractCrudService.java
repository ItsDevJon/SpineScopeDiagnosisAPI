package org.spinescope.diagnosisapi.generic;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class AbstractCrudService<T, ID, R extends CrudRepository<T, ID>> implements CrudService<T, ID> {

    protected final R repository;

    public AbstractCrudService(R repository) {
        this.repository = repository;
    }

    @Override
    public T add(T t) {
        return repository.save(t);
    }

    @Override
    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public Iterable<T> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public T update(T t) {
        return repository.save(t);
    }

}
