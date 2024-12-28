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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        final String ROLE_ADMIN = UserType.ADMIN.name();
        final String ROLE_DOCTOR = UserType.DOCTOR.name();

        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for API endpoints
                .authorizeHttpRequests(auth -> auth

                        // Grant ADMIN full access to all endpoints
                        .requestMatchers("/api/**").hasRole(ROLE_ADMIN)

                        // Grant DOCTOR permission to perform GET requests on /api/patients/**
                        .requestMatchers(HttpMethod.GET, "/api/patients/**").hasRole(ROLE_DOCTOR)

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

}