package com.pizza.controller;

import com.pizza.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.pizza.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public UserDTO getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

//    @PostMapping()
//    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
//        return
//    }

}
