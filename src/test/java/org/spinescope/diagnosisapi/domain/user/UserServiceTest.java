package org.spinescope.diagnosisapi.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String VALID_USERNAME = "testuser";
    private static final String INVALID_USERNAME = "wronguser";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        user = new UserEntity();
        user.setUsername(VALID_USERNAME);
    }

    @Test
    void getByUsername_UserExists_ReturnsUser() {
        // Arrange
        when(userRepository.findByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));

        // Act
        Optional<UserEntity> result = userService.getByUsername(VALID_USERNAME);

        // Assert
        assertEquals(Optional.of(user), result, "Expected result to match the existing user");
        verify(userRepository, times(1)).findByUsername(VALID_USERNAME);
    }

    @Test
    void getByUsername_UserDoesNotExist_ReturnsEmptyOptional() {
        // Arrange
        when(userRepository.findByUsername(INVALID_USERNAME)).thenReturn(Optional.empty());

        // Act
        Optional<UserEntity> result = userService.getByUsername(INVALID_USERNAME);

        // Assert
        assertEquals(Optional.empty(), result, "Expected result to be empty for non-existing user");
        verify(userRepository, times(1)).findByUsername(INVALID_USERNAME);
    }

}
