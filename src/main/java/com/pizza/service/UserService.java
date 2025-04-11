package com.pizza.service;

import com.pizza.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

  UserDTO getUserByUsername(String username);

  void registerUser(UserDTO userDTO);

  UserDetails loadUserById(String id);
}
