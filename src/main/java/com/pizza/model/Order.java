package com.pizza.model;

import com.pizza.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"order\"")
@Entity
@Builder
public class Order {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    @NotBlank
    @Column(name = "delivery_address")
    private String deliveryAddress;

    @NotBlank
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<PizzaOrderItem> pizzas;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<DrinkOrderItem> drinks;

    @CreationTimestamp
    @Column(name = "created_time", updatable = false, nullable = false)
    private LocalDateTime orderedDateTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @Enumerated(EnumType.STRING)
    private Status status;
}
