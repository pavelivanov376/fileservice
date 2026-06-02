package com.pavel.fileservice.config;

import com.pavel.fileservice.security.ServiceTokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration for the File Service.
 *
 * This service does NOT authenticate users directly. Instead, it validates
 * that requests come from a trusted source (API Gateway) via the X-Service-Token header.
 *
 * Authentication flow:
 * 1. API Gateway authenticates the user with Basic Auth
 * 2. Gateway adds X-Service-Token header to the forwarded request
 * 3. This service validates the token and allows/denies access
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ServiceTokenAuthenticationFilter serviceTokenFilter;

    public SecurityConfig(ServiceTokenAuthenticationFilter serviceTokenFilter) {
        this.serviceTokenFilter = serviceTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Disable CSRF for stateless API
                .csrf(AbstractHttpConfigurer::disable)

                // Stateless session - no session cookies
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Add our custom filter before the standard auth filter
                .addFilterBefore(serviceTokenFilter, UsernamePasswordAuthenticationFilter.class)

                // Configure authorization
                .authorizeHttpRequests(auth -> auth
                        // Allow actuator health endpoint (for Kubernetes probes)
                        .requestMatchers("/actuator/health").permitAll()
                        // All other endpoints require authentication (service token)
                        .anyRequest().authenticated()
                )

                .build();
    }
}
