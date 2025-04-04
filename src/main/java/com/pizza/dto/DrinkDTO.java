package com.pizza.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrinkDTO {

    private String id;

    private String name;

    private String description;

    private Double price;
}
