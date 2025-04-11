package com.pizza.config.security;

import com.pizza.config.JwtTokenProvider;
import com.pizza.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Value("${pizzaBE.security.apikey}")
  private String AUTH_TOKEN = "";

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @Autowired private UserService customUserDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String method = request.getMethod();
      String endpoint = request.getRequestURI().toLowerCase();
      if ((method.equals("GET") && endpoint.contains("/pizza"))
          || (method.equals("GET") && endpoint.contains("/drink"))
          || (method.equals("POST") && endpoint.equals("/order"))) {
        String apiKey = request.getHeader("X-API-KEY");
        if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
          logger.info("API KEY MISMATCH: {}" + apiKey);
          throw new BadCredentialsException("Invalid API Key");
        }
        SecurityContextHolder.getContext()
            .setAuthentication(new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES));

      } else {
        String jwt = getJwtFromRequest(request);
        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
          String userId = jwtTokenProvider.getUserIdFromJWT(jwt);
          UserDetails userDetails = customUserDetailsService.loadUserById(userId);
          if (userDetails != null) {
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        }
      }
    } catch (Exception ex) {
      logger.error("failed on set user authentication", ex);
    }

    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7); // Extract the token from "Bearer <token>"
    }
    return null;
  }
}
