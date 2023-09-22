package com.superprice.deliveryservice.orders.repository;

import com.superprice.deliveryservice.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Additional custom queries can be defined here
}
