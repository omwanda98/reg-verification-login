package com.superprice.deliveryservice.products.repository;

import com.superprice.deliveryservice.products.model.Product;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findbycat(@Param("category") String category);
    // Additional custom queries can be defined here
}
