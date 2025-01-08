package org.spinescope.diagnosisapi.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final String VALID_USERNAME = "testuser";
    private static final String INVALID_USERNAME = "wronguser";

    @Mock
    private UserService userService;

    private UserController userController;

    private UserEntity existingUser;
    private UserEntity nonExistingUser;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService);

        existingUser = new UserEntity();
        existingUser.setUsername(VALID_USERNAME);

        nonExistingUser = new UserEntity();
        nonExistingUser.setUsername(INVALID_USERNAME);

    }

    @Test
    void entityExists_UserExists_ReturnsTrue() {
        // Arrange
        when(userService.getByUsername(VALID_USERNAME)).thenReturn(Optional.of(existingUser));

        // Act
        boolean exists = userController.entityExists(existingUser);

        // Assert
        assertTrue(exists, "Expected entityExists to return true for existing user");
    }

    @Test
    void entityExists_UserDoesNotExist_ReturnsFalse() {
        // Arrange
        when(userService.getByUsername(INVALID_USERNAME)).thenReturn(Optional.empty());

        // Act
        boolean exists = userController.entityExists(nonExistingUser);

        // Assert
        assertFalse(exists, "Expected entityExists to return false for non-existing user");
    }

}