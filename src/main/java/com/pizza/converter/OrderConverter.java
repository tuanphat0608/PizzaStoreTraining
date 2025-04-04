package com.pizza.converter;

import com.pizza.dto.*;
import com.pizza.model.*;
import com.pizza.repository.DrinkRepository;
import com.pizza.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderConverter {

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    public OrderDTO convertDTO(Order order) {
        List<PizzaOrderItem> pizzaOrderItems = order.getPizzas();
        List<DrinkOrderItem> drinkOrderItems = order.getDrinks();
        List<OrderPizzaDTO> orderPizzaDTOS = new ArrayList<>();
        List<OrderDrinkDTO> orderDrinkDTOS = new ArrayList<>();

        pizzaOrderItems.forEach(item -> {
            Pizza pizza = item.getPizza();
            orderPizzaDTOS.add(OrderPizzaDTO.builder()
                    .pizzaDTO(PizzaDTO.builder()
                            .id(pizza.getId())
                            .name(pizza.getName())
                            .description(pizza.getDescription())
                            .crust(pizza.getCrust())
                            .size(pizza.getSize())
                            .price(pizza.getPrice())
                            .build())
                    .quantity(item.getQuantity())
                    .build());
        });

        drinkOrderItems.forEach(item -> {
            Drink drink = item.getDrink();
            orderDrinkDTOS.add(OrderDrinkDTO.builder()
                    .drinkDTO(DrinkDTO.builder()
                            .id(drink.getId())
                            .name(drink.getName())
                            .description(drink.getDescription())
                            .price(drink.getPrice())
                            .build())
                    .quantity(item.getQuantity())
                    .build());
        });

        return OrderDTO.builder()
                .id(order.getId())
                .phoneNumber(order.getPhoneNumber())
                .deliveryAddress(order.getDeliveryAddress())
                .createdTime(order.getOrderedDateTime())
                .pizzas(orderPizzaDTOS)
                .drinks(orderDrinkDTOS)
                .status(order.getStatus())
                .build();
    }

}