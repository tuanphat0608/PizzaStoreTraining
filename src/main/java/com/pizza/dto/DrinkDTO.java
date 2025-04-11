package com.pizza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

  @JsonProperty("image_url")
  private String image;
}
