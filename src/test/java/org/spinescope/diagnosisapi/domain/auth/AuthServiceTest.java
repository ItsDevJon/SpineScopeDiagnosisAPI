package org.spinescope.diagnosisapi.domain.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.spinescope.diagnosisapi.domain.user.UserEntity;
import org.spinescope.diagnosisapi.domain.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private static final String VALID_USERNAME = "testuser";
    private static final String VALID_PASSWORD = "password123";
    private static final String INVALID_PASSWORD = "wrongpassword";

    @Mock
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private AuthService authService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setUsername(VALID_USERNAME);
        user.setPassword(passwordEncoder.encode(VALID_PASSWORD));

        userRepository = mock(UserRepository.class);
        authService = new AuthService(userRepository, passwordEncoder);
    }

    @Test
    void verifyUserCredentials_ValidCredentials_ReturnsUser() {
        // Arrange
        when(userRepository.findByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));

        // Act
        Optional<UserEntity> result = authService.verifyUserCredentials(VALID_USERNAME, VALID_PASSWORD);

        // Assert
        assertEquals(Optional.of(user), result);
        verify(userRepository, times(1)).findByUsername(VALID_USERNAME);
    }

    @Test
    void verifyUserCredentials_InvalidCredentials_ReturnsEmptyOptional() {
        // Arrange
        when(userRepository.findByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));

        // Act
        Optional<UserEntity> result = authService.verifyUserCredentials(VALID_USERNAME, INVALID_PASSWORD);

        // Assert
        assertEquals(Optional.empty(), result);
        verify(userRepository, times(1)).findByUsername(VALID_USERNAME);
    }

}