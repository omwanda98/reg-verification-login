package com.superprice.deliveryservice.orders.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDERDETAILS")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailsId;
    private int quantity;

    public OrderDetails() {
        // Default constructor
    }

    public OrderDetails(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return orderDetailsId;
    }

    public void setId(Long orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}