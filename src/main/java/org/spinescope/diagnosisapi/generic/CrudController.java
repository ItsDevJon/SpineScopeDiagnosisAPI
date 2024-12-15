package org.spinescope.diagnosisapi.generic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class CrudController<T, ID> {

    protected final CrudService<T, ID> service;

    public CrudController(CrudService<T, ID> service) {
        this.service = service;
    }

    // Return 201 if created, 400 if the entity already exists
    @PostMapping
    public ResponseEntity<T> create(@RequestBody T newEntity) {

        if (entityExists(newEntity)) {
            return ResponseEntity.badRequest().build();
        }

        T createdEntity = service.add(newEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
    }

    // It should return 200 OK or 404 if list is empty
    @GetMapping
    public ResponseEntity<Iterable<T>> getAll() {

        Iterable<T> entities = service.getAll();

        if (entities.iterator().hasNext()) {
            return ResponseEntity.ok(entities);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable ID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {

        if (service.getById(id).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        T updatedEntity = service.update(entity);

        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ID id) {

        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteById(id);

        return ResponseEntity.ok().build();
    }

    protected abstract boolean entityExists(T newEntity);

}
