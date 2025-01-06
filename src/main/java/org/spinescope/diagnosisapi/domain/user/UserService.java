package org.spinescope.diagnosisapi.domain.user;

import org.spinescope.diagnosisapi.domain.base.AbstractCrudService;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends AbstractCrudService<UserEntity, Integer> {

    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        super(repository);
        this.userRepository = repository;
    }

    public Optional<UserEntity> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
