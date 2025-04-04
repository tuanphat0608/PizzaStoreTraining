package com.pizza.service.implement;

import com.pizza.dto.PizzaDTO;
import com.pizza.model.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.pizza.repository.PizzaRepository;
import com.pizza.service.PizzaService;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public Page<PizzaDTO> getAllPizza(Pageable pageable) {
        Page<Pizza> pizzaDb = pizzaRepository.findAll(pageable);
        return new PageImpl<>(pizzaDb.stream().map(this::convertToDTO).toList(),
                pizzaDb.getPageable(), pizzaDb.getTotalElements());
    }

    private PizzaDTO convertToDTO(Pizza pizza) {
        return PizzaDTO.builder()
                .id(pizza.getId())
                .name(pizza.getName())
                .description(pizza.getDescription())
                .crust(pizza.getCrust())
                .size(pizza.getSize())
                .price(pizza.getPrice())
                .build();
    }
}
