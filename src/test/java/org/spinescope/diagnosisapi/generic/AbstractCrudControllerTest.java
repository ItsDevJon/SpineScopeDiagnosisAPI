package org.spinescope.diagnosisapi.generic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractCrudControllerTest {

    @Mock
    private AbstractCrudService<String, Long> mockService;

    private AbstractCrudController<String, Long, AbstractCrudService<String, Long>> mockController;

    private Long id;
    private String entity;
    private String existingEntity;

    @BeforeEach
    void setUp() {
        id = 1L;
        entity = "entity";
        existingEntity = "existingEntity";

        mockController = Mockito.mock(
                AbstractCrudController.class,
                Mockito.withSettings()
                        .useConstructor(mockService) // Pass the mocked service to the constructor
                        .defaultAnswer(Mockito.CALLS_REAL_METHODS) // Call real methods by default
        );
    }

    @Test
    void create_newEntity_returns201() {

        when(mockController.entityExists(entity)).thenReturn(false);
        when(mockService.add(entity)).thenReturn(entity);

        ResponseEntity<String> response = mockController.create(entity);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    void create_existingEntity_returns400() {

        when(mockController.entityExists(existingEntity)).thenReturn(true);

        ResponseEntity<String> response = mockController.create(existingEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void getAll_emptyList_returns404() {

        when(mockService.getAll()).thenReturn(List.of());

        ResponseEntity<Iterable<String>> response = mockController.getAll();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void getAll_nonEmptyList_returns200() {

        when(mockService.getAll()).thenReturn(List.of(entity));

        ResponseEntity<Iterable<String>> response = mockController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void getById_entityExists_returns200() {

        when(mockService.getById(id)).thenReturn(java.util.Optional.of(entity));

        ResponseEntity<String> response = mockController.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void getById_entityDoesNotExist_returns404() {

        when(mockService.getById(id)).thenReturn(java.util.Optional.empty());

        ResponseEntity<String> response = mockController.getById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void update_entityExists_returns200() {

        when(mockService.getById(id)).thenReturn(java.util.Optional.of(entity));
        when(mockService.update(entity)).thenReturn(entity);

        ResponseEntity<String> response = mockController.update(id, entity);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void update_entityDoesNotExist_returns400() {

        when(mockService.getById(id)).thenReturn(java.util.Optional.empty());

        ResponseEntity<String> response = mockController.update(id, entity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void deleteById_entityExists_returns200() {

        when(mockService.getById(id)).thenReturn(java.util.Optional.of(entity));

        ResponseEntity<Void> response = mockController.deleteById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void deleteById_entityDoesNotExist_returns404() {

        when(mockService.getById(id)).thenReturn(java.util.Optional.empty());

        ResponseEntity<Void> response = mockController.deleteById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
  
}

