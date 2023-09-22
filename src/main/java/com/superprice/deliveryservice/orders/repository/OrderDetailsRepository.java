package com.superprice.deliveryservice.orders.repository;

import com.superprice.deliveryservice.orders.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    // Additional custom queries can be defined here
}
