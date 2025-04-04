package com.pizza.service;

import com.pizza.dto.UserDTO;

public interface UserService {

    UserDTO getUserByUsername(String username);

    void registerUser(UserDTO userDTO);

    String authenticateUser(String username, String password);
}
