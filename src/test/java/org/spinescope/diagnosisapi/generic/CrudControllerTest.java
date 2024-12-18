package org.spinescope.diagnosisapi.generic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrudControllerTest {

    @Mock
    private TestCrudService service;

    private Long id;
    private String entity;
    private String existingEntity;

    private TestCrudController controller;

    @BeforeEach
    void setUp() {
        id = 1L;
        entity = "entity";
        existingEntity = "existingEntity";
        controller = spy(new TestCrudController(service));
    }

    @Test
    void create_newEntity_returns201() {

        when(controller.entityExists(entity)).thenReturn(false);
        when(service.add(entity)).thenReturn(entity);

        ResponseEntity<String> response = controller.create(entity);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    void create_existingEntity_returns400() {

        when(controller.entityExists(existingEntity)).thenReturn(true);

        ResponseEntity<String> response = controller.create(existingEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void getAll_emptyList_returns404() {

        when(service.getAll()).thenReturn(List.of());

        ResponseEntity<Iterable<String>> response = controller.getAll();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void getAll_nonEmptyList_returns200() {

        when(service.getAll()).thenReturn(List.of(entity));

        ResponseEntity<Iterable<String>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void getById_entityExists_returns200() {

        when(service.getById(id)).thenReturn(java.util.Optional.of(entity));

        ResponseEntity<String> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void getById_entityDoesNotExist_returns404() {

        when(service.getById(id)).thenReturn(java.util.Optional.empty());

        ResponseEntity<String> response = controller.getById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void update_entityExists_returns200() {

        when(service.getById(id)).thenReturn(java.util.Optional.of(entity));
        when(service.update(entity)).thenReturn(entity);

        ResponseEntity<String> response = controller.update(id, entity);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void update_entityDoesNotExist_returns400() {

        when(service.getById(id)).thenReturn(java.util.Optional.empty());

        ResponseEntity<String> response = controller.update(id, entity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void deleteById_entityExists_returns200() {

        when(service.getById(id)).thenReturn(java.util.Optional.of(entity));

        ResponseEntity<Void> response = controller.deleteById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void deleteById_entityDoesNotExist_returns404() {

        when(service.getById(id)).thenReturn(java.util.Optional.empty());

        ResponseEntity<Void> response = controller.deleteById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
  
}

class TestCrudController extends AbstractCrudController<String, Long, TestCrudService> {

    public TestCrudController(TestCrudService service) {
        super(service);
    }

    @Override
    protected boolean entityExists(String entity) {
        return "existingEntity".equals(entity);
    }

}

