package com.superprice.deliveryservice.products.service;

import com.superprice.deliveryservice.products.model.Product;
import com.superprice.deliveryservice.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create a new product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Retrieve a product by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Retrieve all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Update a product (assuming product's ID is set)
    public Product updateProduct(Product product) {
        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Product ID is not valid or doesn't exist.");
        }
        return productRepository.save(product);
    }

    // Delete a product by ID
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with ID " + id + " doesn't exist.");
        }
        productRepository.deleteById(id);
    }

    public List<Product> getProductByCat(String category) {
        return productRepository.findbycat(category);
    }
}
