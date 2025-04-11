package com.pizza.repository;

import com.pizza.model.DrinkOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkOrderItemRepository extends JpaRepository<DrinkOrderItem, String> {}
