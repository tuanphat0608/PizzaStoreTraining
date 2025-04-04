package com.pizza.controller;

import com.pizza.dto.PizzaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pizza.service.PizzaService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping()
    public Page<PizzaDTO> getAllPizzas(Pageable pageable) {
        return pizzaService.getAllPizza(pageable);
    }
}
