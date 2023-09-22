package com.superprice.deliveryservice.users.controller;

import com.superprice.deliveryservice.users.DTO.UserLoginDto;
import com.superprice.deliveryservice.users.DTO.UserRegistrationDto;
import com.superprice.deliveryservice.users.model.User;
import com.superprice.deliveryservice.users.repository.UserRepository;
import com.superprice.deliveryservice.users.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!id.equals(user.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    //registration code
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegistrationDto registrationDto) {
        // Perform input validation using Spring's validation annotations
        // For example, you can annotate UserRegistrationDto fields with @NotNull, @Email, etc.

        // Check if the username or email is already in use
        if (userService.existsByUsername(registrationDto.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already in use.");
        }

        if (userService.existsByEmail(registrationDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }

        // Create a User instance from the registration DTO
        User user = new User(registrationDto.getUsername(), registrationDto.getPassword(), registrationDto.getEmail());

        // Save the user to the database
        userService.createUser(user);

        // Send an email verification link to the user's email address
        userService.sendVerificationEmail(user.getEmail());

        return ResponseEntity.ok("Registration successful. Please check your email for verification.");
    }
    
    //Email verification
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        User user = userService.getUserByVerificationToken(token);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid verification token.");
        }

        // Mark the user as verified
        user.setVerified(true);
        userRepository.save(user);

        return ResponseEntity.ok("Email verification successful. You can now log in.");
    }
    
    //login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto loginDto, HttpSession session) {
        // Validate user credentials (e.g., check username and password)
        Optional<User> userOptional = userService.findByUsername(loginDto.getUsername());

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }

        // Store the authenticated user in the session
        session.setAttribute("authenticatedUser", user);

        return ResponseEntity.ok("Login successful.");
    }

}
