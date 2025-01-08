package org.spinescope.diagnosisapi.domain.auth;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spinescope.diagnosisapi.domain.user.UserEntity;
import org.spinescope.diagnosisapi.domain.user.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "password123";

    private static final String VALID_AUTH_HEADER = "Basic dGVzdHVzZXI6cGFzc3dvcmQxMjM="; // Base64 encoded "testuser:password123"
    private static final String INVALID_AUTH_HEADER = "Basic invalidheader";

    @Mock
    private AuthService authService;

    private AuthController authController;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        
        authController = new AuthController(authService);

        user = new UserEntity();
        user.setUsername(USERNAME);
        user.setPassword(new BCryptPasswordEncoder().encode(PASSWORD));
        user.setUserType(UserType.ADMIN);

    }

    @Test
    void login_ValidAuthHeader_ReturnsUserType() {
        // Arrange
        when(authService.verifyUserCredentials(USERNAME, PASSWORD)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<String> response = authController.login(VALID_AUTH_HEADER);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UserType.ADMIN.name(), response.getBody());
    }

    @Test
    void login_InValidAuthHeader_ReturnsUnauthorized() {
        // Arrange: No need to mock `verifyUserCredentials` since if the header is invalid, the method won't be called

        // Act
        ResponseEntity<String> response = authController.login(INVALID_AUTH_HEADER);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid Authorization header", response.getBody());
    }

}