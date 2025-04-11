package com.pizza.service.implement;

import com.pizza.dto.DrinkDTO;
import com.pizza.repository.DrinkRepository;
import com.pizza.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DrinkServiceImpl implements DrinkService {
  @Autowired private DrinkRepository drinkRepository;

  @Override
  public Page<DrinkDTO> getAllDrinks(Pageable pageable) {
    return drinkRepository.findAllDrinkDTO(pageable);
  }
}
