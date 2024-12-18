package org.spinescope.diagnosisapi.generic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CrudController<T, ID> {

    @PostMapping(consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    ResponseEntity<T> create(@RequestBody T t);

    @GetMapping(produces = {"application/json", "application/xml"})
    ResponseEntity<Iterable<T>> getAll();

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    ResponseEntity<T> getById(@PathVariable ID id);

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    ResponseEntity<T> update(@PathVariable ID id, @RequestBody T t);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable ID id);

}
