package com.pizza.repository;

import com.pizza.model.PizzaOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaOrderItemRepository extends JpaRepository<PizzaOrderItem, String> {}
