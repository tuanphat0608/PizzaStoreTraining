package com.pizza.repository;

import com.pizza.dto.DrinkDTO;
import com.pizza.model.Drink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DrinkRepository extends JpaRepository<Drink, String> {

  @Query(
      "select new com.pizza.dto.DrinkDTO(d.id, d.name, d.description, d.price, d.image) from Drink"
          + " d")
  Page<DrinkDTO> findAllDrinkDTO(Pageable pageable);
}
