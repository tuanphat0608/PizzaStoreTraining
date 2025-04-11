package com.pizza.repository;

import com.pizza.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findUserByUsername(String username);

  Optional<User> findByUsername(String username);
}
