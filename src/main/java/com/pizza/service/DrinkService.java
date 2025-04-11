package com.pizza.service;

import com.pizza.dto.DrinkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DrinkService {

  Page<DrinkDTO> getAllDrinks(Pageable pageable);
}
