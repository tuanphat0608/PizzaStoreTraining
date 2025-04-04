package com.pizza.config.security;

import com.pizza.config.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String uri = request.getRequestURI();

        // Allow Swagger UI requests to bypass authentication
        if (uri.contains("/swagger-ui") || uri.contains("/v3/api-docs") || uri.contains("/swagger-resources")) {
            filterChain.doFilter(request, response); // Allow Swagger UI request to proceed
            return;
        }

        // Log request URI and Authorization header for debugging
        logger.info("Request URI: " + uri);
        logger.info("Authorization Header: " + request.getHeader("Authorization"));

        // Get JWT token from Authorization header
        String token = getJwtFromRequest(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String userId = jwtTokenProvider.getUserIdFromJWT(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userId, null, null // You can add roles here if needed
            );

            SecurityContextHolder.getContext().setAuthentication(authentication); // Set authentication in the context
        }

        filterChain.doFilter(request, response); // Continue with the filter chain
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Extract the token from "Bearer <token>"
        }
        return null;
    }
}
