package com.pizza.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPizzaDTO {

    private PizzaDTO pizzaDTO;

    private Long quantity;

}
