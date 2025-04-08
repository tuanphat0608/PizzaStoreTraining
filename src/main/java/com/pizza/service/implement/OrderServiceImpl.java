package com.pizza.service.implement;

import com.pizza.converter.OrderConverter;
import com.pizza.dto.*;
import com.pizza.enums.Status;
import com.pizza.model.*;
import com.pizza.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.pizza.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private DrinkOrderItemRepository drinkOrderItemRepository;

    @Autowired
    private PizzaOrderItemRepository pizzaOrderItemRepository;

    @Autowired
    private OrderConverter converter;


    @Override
    public OrderDTO getOrderDetailByOrderId(String orderId) {
        Optional<Order> orderOpt = orderRepository.findOrderById(orderId);
        if (orderOpt.isPresent()) {
            return converter.convertDTO(orderOpt.get());
        } else throw new NoSuchElementException("Order with ID " + orderId + " not found");
    }

    @Override
    public Page<OrderDTO> findOrdersByStatus(Status status, Pageable pageable) {
        Page<Order> ordersFromDb = orderRepository.findOrdersByStatus(status, pageable);
        return new PageImpl<>(ordersFromDb.stream().map(converter::convertDTO).toList(),
                ordersFromDb.getPageable(), ordersFromDb.getTotalElements());
    }

    @Override
    @Transactional
    public void updateOrderStatus(String orderId, Status status) {
        Optional<Order> orderOpt = orderRepository.findOrderById(orderId);
        if (orderOpt.isPresent()) {
            orderRepository.updateOrderStatus(orderId, status);
        } else throw new NoSuchElementException("Order with ID " + orderId + " not found");
    }

    @Override
    @Transactional
    public void bulkUpdateOrderStatus(BulkUpdateDTO bulkUpdateDTO) {
        orderRepository.bulkUpdateOrderStatus(bulkUpdateDTO.getUpdateIds(), bulkUpdateDTO.getStatus());
    }

    @Override
    @Transactional
    public OrderDTO createNewOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setName(orderDTO.getName());
        order.setStatus(Status.PENDING);
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setDeliveryAddress(orderDTO.getDeliveryAddress());

        Order savedOrder = orderRepository.saveAndFlush(order);

        List<DrinkOrderItem> drinkOrders = new ArrayList<>();
        List<PizzaOrderItem> pizzaOrders = new ArrayList<>();

        for (OrderPizzaDTO pizzaItem : orderDTO.getPizzas()) {
            Optional<Pizza> pizza = pizzaRepository.findById(pizzaItem.getPizzaDTO().getId());
            if (pizza.isPresent()) {
                PizzaOrderItem orderPizza = new PizzaOrderItem();
                orderPizza.setOrder(savedOrder);
                orderPizza.setPizza(pizza.get());
                orderPizza.setQuantity(pizzaItem.getQuantity());
                pizzaOrders.add(orderPizza);
            } else {
                throw new NoSuchElementException("Pizza with ID " + pizzaItem.getPizzaDTO().getId() + " not found");
            }
        }

        for (OrderDrinkDTO drinkItem : orderDTO.getDrinks()) {
            Optional<Drink> drink = drinkRepository.findById(drinkItem.getDrinkDTO().getId());
            if (drink.isPresent()) {
                DrinkOrderItem drinkOrder = new DrinkOrderItem();
                drinkOrder.setOrder(savedOrder);
                drinkOrder.setDrink(drink.get());
                drinkOrder.setQuantity(drinkItem.getQuantity());
                drinkOrders.add(drinkOrder);
            } else {
                throw new NoSuchElementException("Drink with ID " + drinkItem.getDrinkDTO().getId() + " not found");
            }
        }
        pizzaOrderItemRepository.saveAll(pizzaOrders);
        drinkOrderItemRepository.saveAll(drinkOrders);

        savedOrder.setPizzas(pizzaOrders);
        savedOrder.setDrinks(drinkOrders);

        return converter.convertDTO(savedOrder);
    }
}
