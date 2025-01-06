package org.spinescope.diagnosisapi.domain.auth;

import lombok.RequiredArgsConstructor;

import org.spinescope.diagnosisapi.domain.user.UserEntity;
import org.spinescope.diagnosisapi.security.BasicAuthDecoder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String authHeader) {

        // Decode the Basic Auth header
        String[] credentials = BasicAuthDecoder.decodeBasicAuth(authHeader);
        if (credentials == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header");
        }

        String username = credentials[0];
        String password = credentials[1];

        // Verify the credentials with the database
        Optional<UserEntity> optionalUser = authService.verifyUserCredentials(username, password);
        return optionalUser
                .map(userEntity -> ResponseEntity.ok(userEntity.getUserType().name()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password"));

    }

}
