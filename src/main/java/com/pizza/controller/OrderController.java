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

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public OrderDTO getOrderDetail(String orderId) {
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
    public ResponseEntity<String> updateOrderStatus(@RequestParam String orderId, @RequestParam Status status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.status(HttpStatus.OK).body(orderId);
    }

    @PostMapping()
    public OrderDTO createNewOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createNewOrder(orderDTO);
    }

    @PostMapping("/bulk-update")
    public ResponseEntity<List<String>> bulkUpdateOrder(@Valid @RequestBody BulkUpdateDTO bulkUpdateDTO) {
        orderService.bulkUpdateOrderStatus(bulkUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(bulkUpdateDTO.getUpdateIds());
    }

}
