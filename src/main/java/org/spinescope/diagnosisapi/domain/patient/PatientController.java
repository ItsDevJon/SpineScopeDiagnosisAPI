package org.spinescope.diagnosisapi.domain.patient;

import org.spinescope.diagnosisapi.domain.base.AbstractCrudController;
import org.spinescope.diagnosisapi.domain.diagnosis.DiagnosisEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/patients")
public class PatientController extends AbstractCrudController<PatientEntity, Integer, PatientService> {

    public PatientController(PatientService service) {
        super(service);
    }

    @GetMapping("/{patientId}/diagnoses")
    public ResponseEntity<Set<DiagnosisEntity>> getPatientDiagnoses(@PathVariable Integer patientId) {

        Optional<PatientEntity> optionalPatient = service.getById(patientId);

        return optionalPatient.map(patient -> ResponseEntity.ok(patient.getDiagnoses()))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/{patientId}/diagnoses/{diagnosisId}")
    public ResponseEntity<DiagnosisEntity> getDiagnosisById(@PathVariable Integer patientId, @PathVariable Integer diagnosisId) {

        Optional<PatientEntity> optionalPatient = service.getById(patientId);

        return optionalPatient.map(patient -> patient.getDiagnoses().stream()
                .filter(diagnosis -> diagnosis.getId().equals(diagnosisId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build())) // Diagnosis not found
                .orElseGet(() -> ResponseEntity.notFound().build()); // Patient not found

    }

    @Override
    public boolean entityExists(PatientEntity patient) {
        return service.getByNameAndSurname(patient.getName(), patient.getSurname()) != null;
    }

}
