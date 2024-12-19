package org.spinescope.diagnosisapi.domain.patient;

import org.spinescope.diagnosisapi.domain.base.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends AbstractCrudService<Patient, Integer> {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository repository) {
        super(repository);
        this.patientRepository = repository;
    }

    public Patient getByNameAndSurname(String name, String surname) {
        return patientRepository.findByNameAndSurname(name, surname).orElse(null);
    }

}
