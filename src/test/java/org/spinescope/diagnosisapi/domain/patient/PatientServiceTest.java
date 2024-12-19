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
public class PatientServiceTest {

    @Mock
    private PatientRepository mockPatientRepository;

    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this); // Initialize mocks
        patientService = new PatientService(mockPatientRepository);

        // Setup patient object
        patient = new Patient();
        patient.setId(1);
        patient.setName("John");
        patient.setSurname("Doe");
        patient.setAge(30);
    }

    @Test
    void testGetByNameAndSurname_existingPatient_returnsPatient() {
        // Arrange: mock repository to return a patient when searched by name and surname
        when(mockPatientRepository.findByNameAndSurname("John", "Doe")).thenReturn(Optional.of(patient));

        // Act: call the service method
        Patient result = patientService.getByNameAndSurname("John", "Doe");

        // Assert: verify the result matches the expected patient
        assertNotNull(result);
        assertEquals(patient.getId(), result.getId());
        assertEquals(patient.getName(), result.getName());
        assertEquals(patient.getSurname(), result.getSurname());

        // Verify that the repository method was called with the correct arguments
        verify(mockPatientRepository, times(1)).findByNameAndSurname("John", "Doe");
    }

    @Test
    void testGetByNameAndSurname_patientNotFound_returnsNull() {
        // Arrange: mock repository to return empty when no patient is found
        when(mockPatientRepository.findByNameAndSurname("Jane", "Doe")).thenReturn(Optional.empty());

        // Act: call the service method
        Patient result = patientService.getByNameAndSurname("Jane", "Doe");

        // Assert: verify that the result is null when no patient is found
        assertNull(result);

        // Verify that the repository method was called with the correct arguments
        verify(mockPatientRepository, times(1)).findByNameAndSurname("Jane", "Doe");
    }

}