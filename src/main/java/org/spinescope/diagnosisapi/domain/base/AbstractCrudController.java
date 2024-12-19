package org.spinescope.diagnosisapi.domain.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractCrudController<T, ID, S extends AbstractCrudService<T, ID>> implements CrudController<T, ID> {

    protected final S service;

    protected AbstractCrudController(S service) {
        this.service = service;
    }

    // Return 201 if created, 400 if the entity already exists
    @Override
    public ResponseEntity<T> create(T newEntity) {

        if (entityExists(newEntity)) {
            return ResponseEntity.badRequest().build();
        }

        T createdEntity = service.add(newEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
    }

    // It should return 200 OK or 404 if list is empty
    @Override
    public ResponseEntity<Iterable<T>> getAll() {

        Iterable<T> entities = service.getAll();

        if (entities.iterator().hasNext()) {
            return ResponseEntity.ok(entities);
        }

        return ResponseEntity.notFound().build();

    }

    @Override
    public ResponseEntity<T> getById(ID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<T> update(ID id, T entity) {

        if (service.getById(id).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        T updatedEntity = service.update(entity);

        return ResponseEntity.ok(updatedEntity);
    }

    @Override
    public ResponseEntity<Void> deleteById(ID id) {

        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteById(id);

        return ResponseEntity.ok().build();
    }

    protected abstract boolean entityExists(T newEntity);

}
