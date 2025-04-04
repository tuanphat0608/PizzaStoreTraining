package com.pizza.controller;

import com.pizza.dto.DrinkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pizza.service.DrinkService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drinks")
public class DrinkController {

    @Autowired
    private DrinkService drinkService;

    @GetMapping()
    public Page<DrinkDTO> getAllDrinks(Pageable pageable) {
        return drinkService.getAllDrinks(pageable);
    }
}
