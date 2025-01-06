package org.spinescope.diagnosisapi.security;

import org.spinescope.diagnosisapi.domain.user.UserType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        final String ROLE_ADMIN = UserType.ADMIN.name();
        final String ROLE_DOCTOR = UserType.DOCTOR.name();

        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for API endpoints
                .cors(Customizer.withDefaults())  // Enable CORS
                .authorizeHttpRequests(auth -> auth

                        // Grant DOCTOR permission to perform GET requests on /api/patients/**
                        .requestMatchers(HttpMethod.POST, "/auth/login").hasAnyRole(ROLE_ADMIN, ROLE_DOCTOR)
                        .requestMatchers("/api/patients/**").hasAnyRole(ROLE_ADMIN, ROLE_DOCTOR)

                        // Grant ADMIN full access to all endpoints
                        .requestMatchers("/api/**").hasRole(ROLE_ADMIN)

                        // Fallback for any other requests
                        .anyRequest().authenticated()

                )
                .httpBasic(Customizer.withDefaults())  // Enable Basic Auth
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Make it stateless, standard for REST APIs
                );

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allowed origins
        config.setAllowedOrigins(List.of("http://localhost:5173"));

        // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow all headers
        config.setAllowedHeaders(List.of("*"));

        // Allow credentials (cookies, authorization headers, etc.)
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}