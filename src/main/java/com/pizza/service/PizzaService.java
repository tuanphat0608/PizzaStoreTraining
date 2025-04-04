package com.pizza.service;

import com.pizza.dto.PizzaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PizzaService {

    Page<PizzaDTO> getAllPizza(Pageable pageable);
}
