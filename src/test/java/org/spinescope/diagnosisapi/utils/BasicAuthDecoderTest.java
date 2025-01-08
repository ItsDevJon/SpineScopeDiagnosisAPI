package org.spinescope.diagnosisapi.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class BasicAuthDecoderTest {

    @Test
    void decodeBasicAuth_ValidHeader_returnsUsernameAndPassword() {
        String header = "Basic dXNlcm5hbWU6cGFzc3dvcmQ="; // "username:password" in Base64
        Optional<String[]> result = BasicAuthDecoder.decodeBasicAuth(header);

        assertTrue(result.isPresent(), "Result should be present for valid header");
        String[] credentials = result.get();
        assertEquals("username", credentials[0], "Username should match");
        assertEquals("password", credentials[1], "Password should match");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "dXNlcm5hbWU6cGFzc3dvcmQ="}) // Empty header and missing prefix
    void decodeBasicAuth_InvalidOrMissingPrefix_returnsEmptyOptional(String header) {
        Optional<String[]> result = BasicAuthDecoder.decodeBasicAuth(header);

        assertFalse(result.isPresent(), "Result should not be present for invalid or missing prefix");
    }

    @Test
    void decodeBasicAuth_NullHeader_returnsEmptyOptional() {
        Optional<String[]> result = BasicAuthDecoder.decodeBasicAuth(null);

        assertFalse(result.isPresent(), "Result should not be present for null header");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Basic invalid_base64", "Basic dXNlcm5hbWU="}) // Invalid Base64 and credentials without colon
    void decodeBasicAuth_InvalidBase64OrNoColon_returnsEmptyOptional(String header) {
        Optional<String[]> result = BasicAuthDecoder.decodeBasicAuth(header);

        assertFalse(result.isPresent(), "Result should not be present for invalid Base64 or credentials format");
    }
}