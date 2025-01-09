package org.spinescope.diagnosisapi.domain.patient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spinescope.diagnosisapi.domain.diagnosis.DiagnosisEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private PatientService mockService;

    private PatientController mockController;

    private PatientEntity patient;
    private DiagnosisEntity diagnosis;

    @BeforeEach
    void setUp() {
        mockController = new PatientController(mockService); // Use the real controller

        patient = new PatientEntity();
        patient.setId(1);
        patient.setName("John");
        patient.setSurname("Doe");
        patient.setAge(25);
        patient.setGender(Gender.MALE);

        diagnosis = new DiagnosisEntity();
        diagnosis.setId(1);
        diagnosis.setDate(LocalDate.now());
        diagnosis.setDisease("Scoliosis");

        patient.setDiagnoses(Set.of(diagnosis)); // Add diagnosis to the patient
    }

    @Test
    void getPatientDiagnoses_existingPatient_returnsDiagnoses() {
        when(mockService.getById(1)).thenReturn(Optional.of(patient));

        ResponseEntity<Set<DiagnosisEntity>> response = mockController.getPatientDiagnoses(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patient.getDiagnoses(), response.getBody());
    }

    @Test
    void getPatientDiagnoses_nonExistingPatient_returns404() {
        when(mockService.getById(1)).thenReturn(Optional.empty());

        ResponseEntity<Set<DiagnosisEntity>> response = mockController.getPatientDiagnoses(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getDiagnosisById_existingPatientAndDiagnosis_returnsDiagnosis() {
        when(mockService.getById(1)).thenReturn(Optional.of(patient));

        ResponseEntity<DiagnosisEntity> response = mockController.getDiagnosisById(1, diagnosis.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(diagnosis, response.getBody());
    }

    @Test
    void getDiagnosisById_existingPatientAndNonExistingDiagnosis_returns404() {
        when(mockService.getById(1)).thenReturn(Optional.of(patient));

        ResponseEntity<DiagnosisEntity> response = mockController.getDiagnosisById(patient.getId(), 2); // Non-existing diagnosis ID

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getDiagnosisById_nonExistingPatient_returns404() {
        when(mockService.getById(55)).thenReturn(Optional.empty());

        ResponseEntity<DiagnosisEntity> response = mockController.getDiagnosisById(55, diagnosis.getId()); // Non-existing patient ID

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void entityExists_existingPatient_returnsTrue() {
        when(mockService.getByNameAndSurname(patient.getName(), patient.getSurname())).thenReturn(patient);

        assertTrue(mockController.entityExists(patient));
    }

    @Test
    void entityExists_nonExistingPatient_returnsFalse() {
        when(mockService.getByNameAndSurname(patient.getName(), patient.getSurname())).thenReturn(null);

        assertFalse(mockController.entityExists(patient));
    }

}