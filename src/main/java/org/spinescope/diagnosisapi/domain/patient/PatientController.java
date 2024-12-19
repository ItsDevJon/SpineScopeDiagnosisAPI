package org.spinescope.diagnosisapi.domain.patient;

import org.spinescope.diagnosisapi.domain.base.AbstractCrudController;
import org.spinescope.diagnosisapi.domain.diagnosis.Diagnosis;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/patients")
public class PatientController extends AbstractCrudController<Patient, Integer, PatientService> {

    public PatientController(PatientService service) {
        super(service);
    }

    @GetMapping("/{patientId}/diagnoses")
    public ResponseEntity<Set<Diagnosis>> getPatientDiagnoses(@PathVariable Integer patientId) {

        Optional<Patient> optionalPatient = service.getById(patientId);

        return optionalPatient.map(patient -> ResponseEntity.ok(patient.getDiagnoses()))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/{patientId}/diagnoses/{diagnosisId}")
    public ResponseEntity<Diagnosis> getDiagnosisById(@PathVariable Integer patientId, @PathVariable Integer diagnosisId) {

        Optional<Patient> optionalPatient = service.getById(patientId);

        return optionalPatient.map(patient -> patient.getDiagnoses().stream()
                .filter(diagnosis -> diagnosis.getId().equals(diagnosisId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build())) // Diagnosis not found
                .orElseGet(() -> ResponseEntity.notFound().build()); // Patient not found

    }

    @Override
    public boolean entityExists(Patient patient) {
        return service.getByNameAndSurname(patient.getName(), patient.getSurname()) != null;
    }

}
