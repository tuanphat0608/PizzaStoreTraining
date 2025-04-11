package com.pizza.service.implement;

import com.pizza.config.CustomUserDetails;
import com.pizza.dto.UserDTO;
import com.pizza.enums.Role;
import com.pizza.model.User;
import com.pizza.repository.UserRepository;
import com.pizza.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Autowired private BCryptPasswordEncoder passwordEncoder;

  @Override
  public UserDTO getUserByUsername(String username) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    return UserDTO.builder().username(username).role(user.getRole().name()).build();
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<User> userOpt = userRepository.findByUsername(username);
    if (userOpt.isEmpty()) {
      throw new UsernameNotFoundException(username);
    }
    return new CustomUserDetails(userOpt.get());
  }

  @Override
  public void registerUser(UserDTO registerRequest) {
    User user = new User();
    user.setUsername(registerRequest.getUsername());
    Optional<User> userOpt = userRepository.findByUsername(registerRequest.getUsername());
    if (userOpt.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exist");
    }
    user.setUsername(registerRequest.getUsername());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Encode password
    user.setRole(Role.valueOf(registerRequest.getRole()));

    userRepository.save(user);
  }

  @Override
  public UserDetails loadUserById(String id) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

    return new CustomUserDetails(user);
  }
}
