package com.pizza.controller;

import com.pizza.dto.BulkUpdateDTO;
import com.pizza.dto.OrderDTO;
import com.pizza.enums.Status;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pizza.service.OrderService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public OrderDTO getOrderDetail(@PathVariable String orderId) {
        return orderService.getOrderDetailByOrderId(orderId);
    }

    @GetMapping()
    public Page<OrderDTO> findOrdersByStatus(@Valid @Parameter(
                                                         description = "The status of the order",
                                                         required = true)
                                                 @RequestParam Status status, Pageable pageable) {
        return orderService.findOrdersByStatus(status, pageable);
    }

    @PostMapping("/{orderId}")
    public OrderDTO updateOrderStatus(@PathVariable String orderId, @RequestParam Status status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @PostMapping()
    public OrderDTO createNewOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createNewOrder(orderDTO);
    }

    @PostMapping("/bulk-update")
    public Map<String, String> bulkUpdateOrder(@Valid @RequestBody BulkUpdateDTO bulkUpdateDTO) {
        orderService.bulkUpdateOrderStatus(bulkUpdateDTO);
        return Map.of("ids", String.join("update ", bulkUpdateDTO.getUpdateIds()));
    }

}
