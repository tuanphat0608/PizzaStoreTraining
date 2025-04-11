package com.pizza.repository;

import com.pizza.model.Pizza;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, String> {

  Page<Pizza> findAll(Pageable pageable);
}
