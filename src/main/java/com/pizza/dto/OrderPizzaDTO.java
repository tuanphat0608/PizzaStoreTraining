package com.pizza.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPizzaDTO {

    private PizzaDTO pizza;

    private Long quantity;

}
