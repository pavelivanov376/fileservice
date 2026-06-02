package com.pavel.fileservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filter that validates service-to-service authentication via X-Service-Token header.
 *
 * This filter:
 * 1. Extracts the X-Service-Token header from incoming requests
 * 2. Validates it against the expected service token
 * 3. If valid, creates an authentication context allowing the request to proceed
 * 4. If invalid or missing, the request will be rejected by Spring Security
 *
 * This ensures only requests from the API Gateway (or other trusted services with the token)
 * can access this service's endpoints.
 */
@Component
public class ServiceTokenAuthenticationFilter extends OncePerRequestFilter {

    public static final String SERVICE_TOKEN_HEADER = "X-Service-Token";
    public static final String AUTHENTICATED_USER_HEADER = "X-Authenticated-User";

    @Value("${app.service.token}")
    private String expectedServiceToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String serviceToken = request.getHeader(SERVICE_TOKEN_HEADER);
        String authenticatedUser = request.getHeader(AUTHENTICATED_USER_HEADER);

        // Validate service token
        if (serviceToken != null && serviceToken.equals(expectedServiceToken)) {
            // Token is valid - create authentication
            String principal = authenticatedUser != null ? authenticatedUser : "service-account";

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            principal,
                            null, // No credentials needed - token already validated
                            List.of(new SimpleGrantedAuthority("ROLE_SERVICE"))
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // If token is invalid/missing, no authentication is set
        // Spring Security will reject the request with 401

        filterChain.doFilter(request, response);
    }
}
