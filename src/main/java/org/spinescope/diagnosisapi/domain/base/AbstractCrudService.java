package org.spinescope.diagnosisapi.domain.base;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class AbstractCrudService<T, ID> implements CrudService<T, ID> {

    protected final CrudRepository<T, ID> repository;

    protected AbstractCrudService(CrudRepository<T, ID> repository) {
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
