package org.spinescope.diagnosisapi.domain.patient;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository mockPatientRepository;

    private PatientService patientService;

    private PatientEntity patient;

    @BeforeEach
    void setUp() {
        patientService = new PatientService(mockPatientRepository);

        patient = new PatientEntity();
        patient.setId(1);
        patient.setName("John");
        patient.setSurname("Doe");
        patient.setAge(30);
    }

    @Test
    void testGetByNameAndSurname_existingPatient_returnsPatient() {

        // Arrange
        when(mockPatientRepository.findByNameAndSurname("John", "Doe")).thenReturn(Optional.of(patient));

        // Act
        PatientEntity result = patientService.getByNameAndSurname("John", "Doe");

        // Assert
        assertNotNull(result);
        assertEquals(patient.getId(), result.getId());
        assertEquals(patient.getName(), result.getName());
        assertEquals(patient.getSurname(), result.getSurname());

        // Verify
        verify(mockPatientRepository, times(1)).findByNameAndSurname("John", "Doe");

    }

    @Test
    void testGetByNameAndSurname_patientNotFound_returnsNull() {

        // Arrange
        when(mockPatientRepository.findByNameAndSurname("Jane", "Doe")).thenReturn(Optional.empty());

        // Act
        PatientEntity result = patientService.getByNameAndSurname("Jane", "Doe");

        // Assert
        assertNull(result);

        // Verify
        verify(mockPatientRepository, times(1)).findByNameAndSurname("Jane", "Doe");

    }

}