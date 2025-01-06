package org.spinescope.diagnosisapi.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthDecoder {

    /**
     * Decodes the Authorization header and extracts the username and password.
     *
     * @param authorizationHeader The Authorization header value.
     * @return A String array with [username, password] or null if invalid.
     */
    public static String[] decodeBasicAuth(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            return null;
        }

        // Remove the "Basic " prefix
        String base64Credentials = authorizationHeader.substring("Basic ".length());

        try {
            // Decode the Base64 string
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedBytes, StandardCharsets.UTF_8);

            // Split into username and password
            String[] parts = credentials.split(":", 2);
            if (parts.length != 2) {
                return null;
            }

            return parts; // [username, password]
        } catch (IllegalArgumentException e) {
            // Handle invalid Base64 input
            return null;
        }
    }
}