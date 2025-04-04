package com.pizza.service;

import com.pizza.dto.BulkUpdateDTO;
import com.pizza.dto.OrderDTO;
import com.pizza.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDTO getOrderDetailByOrderId(String orderId);

    Page<OrderDTO> findOrdersByStatus(Status status, Pageable pageable);

    void updateOrderStatus(String orderId, Status status);

    OrderDTO createNewOrder(OrderDTO orderDTO);

    void bulkUpdateOrderStatus(BulkUpdateDTO bulkUpdateDTO);
}
