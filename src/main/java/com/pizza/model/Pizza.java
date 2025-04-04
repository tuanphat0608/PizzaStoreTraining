package com.pizza.model;

import com.pizza.enums.Crust;
import com.pizza.enums.Size;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pizza")
@Entity
@Builder
public class Pizza {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotBlank
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Crust crust;

    @Enumerated(EnumType.STRING)
    private Size size;

    @NotBlank
    private Double price;

}
