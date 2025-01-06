package org.spinescope.diagnosisapi.domain.auth;

import org.spinescope.diagnosisapi.domain.user.UserEntity;
import org.spinescope.diagnosisapi.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserEntity> verifyUserCredentials(String username, String rawPassword) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.filter(u -> passwordEncoder.matches(rawPassword, u.getPassword()));
    }

}
