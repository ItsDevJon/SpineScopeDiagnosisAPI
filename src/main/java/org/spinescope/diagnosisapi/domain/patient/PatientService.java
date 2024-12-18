package org.spinescope.diagnosisapi.domain.patient;

import org.spinescope.diagnosisapi.generic.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends AbstractCrudService<Patient, Integer> {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Patient getByNameAndSurname(String name, String surname) {
        return repository.findByNameAndSurname(name, surname).orElse(null);
    }

}
