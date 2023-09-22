package com.superprice.deliveryservice.users.repository;

import com.superprice.deliveryservice.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByVerificationToken(String token);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email); // Add this line
    boolean existsByUsername(String username); // Add this line
    // Additional custom queries can be defined here
}
