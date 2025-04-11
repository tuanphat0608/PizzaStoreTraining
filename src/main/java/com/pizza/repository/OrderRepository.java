package com.pizza.repository;

import com.pizza.enums.Status;
import com.pizza.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

  Optional<Order> findOrderById(String id);

  Page<Order> findAll(Pageable pageable);

  @Query("select o from Order o where o.status = :status")
  Page<Order> findOrdersByStatus(Status status, Pageable pageable);

  @Modifying
  @Query("update Order o set o.status = :status where o.id = :orderId")
  void updateOrderStatus(String orderId, Status status);

  @Modifying
  @Query("update Order o set o.status = :status where o.id in :orderIds")
  void bulkUpdateOrderStatus(List<String> orderIds, Status status);
}
