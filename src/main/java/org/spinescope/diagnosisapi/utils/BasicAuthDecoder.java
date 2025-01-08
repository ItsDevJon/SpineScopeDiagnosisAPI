package org.spinescope.diagnosisapi.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

public class BasicAuthDecoder {

    private static final String BASIC_PREFIX = "Basic ";

    private BasicAuthDecoder() {
        // Utility class; prevent instantiation
    }

    /**
     * Decodes the Authorization header and extracts the username and password.
     *
     * @param authorizationHeader The Authorization header value.
     * @return An Optional containing a String array with [username, password] or an empty Optional if invalid.
     */
    public static Optional<String[]> decodeBasicAuth(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(BASIC_PREFIX)) {
            return Optional.empty();
        }

        String base64Credentials = authorizationHeader.substring(BASIC_PREFIX.length());

        try {
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            String[] parts = credentials.split(":", 2);

            return parts.length == 2 ? Optional.of(parts) : Optional.empty();
        } catch (IllegalArgumentException e) {
            // Invalid Base64 input
            return Optional.empty();
        }
    }
}