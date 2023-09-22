package com.superprice.deliveryservice;


import com.superprice.deliveryservice.products.controller.ProductController;
import com.superprice.deliveryservice.products.model.Product;
import com.superprice.deliveryservice.products.repository.ProductRepository;
import com.superprice.deliveryservice.products.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductsearchbycatagoryTest {
    ProductController controller;
    ProductService service;
    @Mock
    private ProductRepository repository;

    @BeforeEach
    void setup() {

        this.service = new ProductService(repository);
    }
    @Test
    void emptytest() {
        assertEquals(0, this.service.getProductByCat("fruit").size());
    }
    @Test
    void nonemptytest() {
        Product pro = new Product("apple", 1.0, "fruit");
        this.service.createProduct(pro);
        List<Product> list = new ArrayList<Product>();
        list.add(pro);
        when(repository.findbycat("fruit")).thenReturn(list);
        assertEquals(1, this.service.getProductByCat("fruit").size());
    }
    @Test
    void test2products() {
        Product pro = new Product("apple", 1.0, "fruit");
        Product pro2 = new Product("pear", 1.0, "fruit");
        this.service.createProduct(pro);
        this.service.createProduct(pro2);
        List<Product> list = new ArrayList<Product>();
        list.add(pro);
        list.add(pro2);
        when(repository.findbycat("fruit")).thenReturn(list);
        assertEquals(2, this.service.getProductByCat("fruit").size());
    }
}