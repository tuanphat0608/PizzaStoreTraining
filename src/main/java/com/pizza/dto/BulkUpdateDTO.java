package com.pizza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pizza.enums.Status;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BulkUpdateDTO {

  @JsonProperty("update_ids")
  @NotEmpty(message = "Update IDs cannot be empty")
  List<String> updateIds;

  Status status;
}
