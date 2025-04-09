package com.pizza.controller;

import com.pizza.config.CustomUserDetails;
import com.pizza.config.JwtTokenProvider;
import com.pizza.dto.LoginRequest;
import com.pizza.dto.LoginResponse;
import com.pizza.dto.UserDTO;
import com.pizza.model.User;
import com.pizza.repository.UserRepository;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.pizza.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    public AuthController(UserService userService,
                          UserRepository userRepository,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    // Register Method
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO registerRequest) {
        try {
            userService.registerUser(registerRequest);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error during registration: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<User> loginUser = userRepository.findByUsername(loginRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            return new LoginResponse(jwt);
        } catch (Exception e) {
            System.out.println("Cannot authenticate this user");
            throw e;
        }
    }

    @PostMapping("/me")
    public UserDTO getUserInfo(Authentication authentication) {
        Optional<User> userOpt = userRepository.findByUsername(authentication.getName());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return UserDTO.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .role(user.getRole().name())
                    .build();
        } else throw new UsernameNotFoundException("User not found with id : " + authentication.getName());
    }

    @GetMapping("/$validate-token")
    public Boolean validateToken(@RequestParam(name = "token", required = false) String jwt) {
        if(StringUtils.isBlank(jwt)) return false;
        else return tokenProvider.validateToken(jwt);
    }

}
