package com.pizza.config.security;

import com.pizza.config.JwtTokenProvider;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html", // old path
            "/webjars/**",
            "/v3/api-docs/**",  // OpenAPI 3.0 docs
            "/swagger-ui/**",   // New Swagger UI path
            "/actuator/**",
            "/auth/login",
            "/auth/register"
    };

    private final JwtTokenProvider jwtTokenProvider;

    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(antMatcher(HttpMethod.POST, "/orders")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET, "/drinks")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET, "/pizzas")).permitAll()
                                .requestMatchers(AUTH_WHITELIST).permitAll()  // Allow public access to Swagger UI and other whitelisted paths
                                .anyRequest()
                                .authenticated()  // Require authentication for any other requests
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(
                                "spring_oauth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .description("APIKEY")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("X_API_KEY")
                        )
                        .addSecuritySchemes(
                                "spring_oauth2",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .description("Oauth2 flow")
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .security(Collections.singletonList(
                        new SecurityRequirement().addList("spring_oauth").addList("spring_oauth2")));
    }

}
