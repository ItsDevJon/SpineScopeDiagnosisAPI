package org.spinescope.diagnosisapi.domain.user;

import org.spinescope.diagnosisapi.domain.base.AbstractCrudService;
import org.spinescope.diagnosisapi.domain.patient.Patient;
import org.spinescope.diagnosisapi.domain.patient.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCrudService<UserEntity, Integer> {

    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        super(repository);
        this.userRepository = repository;
    }

    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

}
