package com.sptraders.sptraders_billing.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filter that validates JWT token from Authorization headers.
 * Sets the Spring Security authentication context if token is valid.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Extract 'Authorization' header from the incoming request
        final String authorizationHeader = request.getHeader("Authorization");

        // Check if header is present and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract token from header
            String jwtToken = authorizationHeader.substring(7);

            try {
                // Validate token and parse claims
                if (jwtUtil.validateToken(jwtToken)) {
                    // Extract username and role from the token
                    String username = jwtUtil.extractUsername(jwtToken);
                    String role = jwtUtil.extractRole(jwtToken); // role should be e.g., "ROLE_USER"

                    // Create GrantedAuthority from role string
                    var authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

                    // Create authentication token for Spring Security context
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication context to indicate the user is authenticated
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception ex) {
                // Optional: Log or handle the exception as needed
                // e.g. logger.warn("JWT token validation failed: " + ex.getMessage());
            }
        }

        // Continue filter chain whether token was valid or not
        filterChain.doFilter(request, response);
    }
}
