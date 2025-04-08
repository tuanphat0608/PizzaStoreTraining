package com.pizza.service;

import com.pizza.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDTO getUserByUsername(String username);

    void registerUser(UserDTO userDTO);

    String authenticateUser(String username, String password);

    UserDetails loadUserById(String id);
}
