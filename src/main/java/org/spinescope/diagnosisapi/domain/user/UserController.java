package org.spinescope.diagnosisapi.domain.user;

import org.spinescope.diagnosisapi.domain.base.AbstractCrudController;
import org.spinescope.diagnosisapi.domain.diagnosis.Diagnosis;
import org.spinescope.diagnosisapi.domain.patient.Patient;
import org.spinescope.diagnosisapi.domain.patient.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/users")
public class UserController extends AbstractCrudController<UserEntity, Integer, UserService> {

    public UserController(UserService service) {
        super(service);
    }

    @Override
    public boolean entityExists(UserEntity user) {
        return service.getByUsername(user.getUsername()) != null;
    }

}
