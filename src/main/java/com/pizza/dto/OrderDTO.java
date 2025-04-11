package com.pizza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pizza.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

  private String id;

  @NotBlank private String name;

  @JsonProperty("phone_number")
  @NotBlank
  private String phoneNumber;

  @JsonProperty("delivery_address")
  @NotBlank
  private String deliveryAddress;

  private List<OrderPizzaDTO> pizzas;

  private List<OrderDrinkDTO> drinks;

  @Schema(description = "Status of the order")
  private Status status;

  @JsonProperty("ordered_date_time")
  private LocalDateTime createdTime;

  @JsonProperty("updated_time")
  private LocalDateTime updatedTime;

  @JsonProperty("total_price")
  private Double totalPrice;
}
