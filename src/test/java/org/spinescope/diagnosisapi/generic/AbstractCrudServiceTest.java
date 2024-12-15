package org.spinescope.diagnosisapi.generic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbstractCrudServiceTest {

    @Mock
    private CrudRepository<String, Long> repository;

    @InjectMocks
    private TestCrudService service;

    @Test
    void add_ReturnsEntity() {

        // Arrange
        String entity = "entity";

        // Act
        when(repository.save(Mockito.any(String.class))).thenReturn(entity);

        String result = service.add(entity);

        // Assert
        assertThat(result).isNotNull();

    }

    @Test
    void getById_ReturnsEntity() {

        // Arrange
        Long id = 1L;
        String entity = "entity";

        // Act
        when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(entity));

        String result = service.getById(id).orElse(null);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    void existsById_ReturnsTrue() {

        // Arrange
        Long id = 1L;

        // Act
        when(repository.existsById(Mockito.any(Long.class))).thenReturn(true);

        boolean result = service.existsById(id);

        // Assert
        assertThat(result).isTrue();

    }

    @Test
    void getAll_ReturnsEntities() {

        // Arrange
        String entity = "entity";

        // Act
        when(repository.findAll()).thenReturn(List.of(entity));

        Iterable<String> result = service.getAll();

        // Assert
        assertThat(result).isNotEmpty();

    }

    @Test
    void deleteById_Void() {

        // Arrange
        Long id = 1L;
        String entity = "entity";

        // Act
        // when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(entity));
        doNothing().when(repository).deleteById(id);

        service.deleteById(id);

        // Assert
        verify(repository, times(1)).deleteById(id);
        // assertAll(() -> service.deleteById(id));

    }

    @Test
    void update_ReturnsEntity() {

        // Arrange
        String entity = "entity";

        // Act
        when(repository.save(Mockito.any(String.class))).thenReturn(entity);

        String result = service.update(entity);

        // Assert
        assertThat(result).isNotNull();
    }

}