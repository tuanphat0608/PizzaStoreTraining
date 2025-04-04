package com.pizza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pizza.enums.Status;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Getter
@Setter
public class BulkUpdateDTO {

    @JsonProperty("update_ids")
    @NotEmpty(message = "Update IDs cannot be empty")
    List<String> updateIds;

    Status status;
}
