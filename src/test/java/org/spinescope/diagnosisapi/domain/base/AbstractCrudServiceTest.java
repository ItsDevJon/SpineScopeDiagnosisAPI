package org.spinescope.diagnosisapi.domain.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.data.repository.CrudRepository;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbstractCrudServiceTest {

    @Mock
    private CrudRepository<String, Long> mockRepository;

    private AbstractCrudService<String, Long> service;

    private Long id;
    private String entity1;
    private String entity2;

    @BeforeEach
    void setUp() {

        id = 1L;
        entity1 = "Entity1";
        entity2 = "Entity2";

        // Create an anonymous concrete implementation of AbstractCrudService for testing
        service = new AbstractCrudService<>(mockRepository) {};
    }
    
    @Test
    void add_ReturnsEntity() {

        // Act
        when(mockRepository.save( anyString() )).thenReturn(entity1);

        String result = service.add(entity1);

        // Assert
        assertEquals(entity1, result);

        verify(mockRepository).save(entity1);

    }

    @Test
    void getById_ReturnsEntity() {

        // Act
        when(mockRepository.findById( anyLong() )).thenReturn(Optional.of(entity1));

        // Assert
        Optional<String> result = service.getById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(entity1, result.get());

        verify(mockRepository).findById(id);

    }

    @Test
    void existsById_ReturnsTrue() {

        // Act
        when(mockRepository.existsById( anyLong() )).thenReturn(true);

        boolean exists = service.existsById(id);

        // Assert
        assertTrue(exists);

        verify(mockRepository).existsById(id);

    }

    @Test
    void getAll_ReturnsEntities() {

        // Arrange
        List<String> entities = List.of(entity1, entity2);

        // Act
        when(mockRepository.findAll()).thenReturn(entities);

        Iterable<String> result = service.getAll();

        // Assert
        assertEquals(entities, result);

        verify(mockRepository).findAll();

    }

    @Test
    void deleteById_Void() {

        // Act
        doNothing().when(mockRepository).deleteById(id);

        service.deleteById(id);

        // Assert
        verify(mockRepository, times(1)).deleteById(id);

    }

    @Test
    void update_ReturnsEntity() {

        // Act
        when(mockRepository.save( anyString() )).thenReturn(entity1);

        String result = service.update(entity1);

        // Assert
        assertEquals(entity1, result);

        verify(mockRepository).save(entity1);

    }

}