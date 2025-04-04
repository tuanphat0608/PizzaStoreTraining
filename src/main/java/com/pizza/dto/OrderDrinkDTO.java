package com.pizza.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDrinkDTO {

    private DrinkDTO drinkDTO;

    private Long quantity;

}
