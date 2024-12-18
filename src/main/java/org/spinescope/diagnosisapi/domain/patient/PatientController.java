package org.spinescope.diagnosisapi.domain.patient;

import org.spinescope.diagnosisapi.generic.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/patients")
public class PatientController extends AbstractCrudController<Patient, Integer, PatientService> {

    private final PatientService service;

    public PatientController(PatientService service) {
        super(service);
        this.service = service;
    }

    @Override
    public boolean entityExists(Patient patient) {
        return service.getByNameAndSurname(patient.getName(), patient.getSurname()) != null;
    }

}
