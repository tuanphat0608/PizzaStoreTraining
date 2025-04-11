package com.pizza.controller;

import com.pizza.dto.DrinkDTO;
import com.pizza.service.DrinkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drinks")
public class DrinkController {

  private final DrinkService drinkService;

  public DrinkController(DrinkService drinkService) {
    this.drinkService = drinkService;
  }

  @GetMapping()
  public Page<DrinkDTO> getAllDrinks(Pageable pageable) {
    return drinkService.getAllDrinks(pageable);
  }
}
