package com.pizza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pizza.enums.Crust;
import com.pizza.enums.Size;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PizzaDTO {

    private String id;

    private String name;

    private String description;

    private Crust crust;

    private Size size;

    private Double price;

    @JsonProperty("image_url")
    private String image;

}
