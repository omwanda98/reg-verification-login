package com.superprice.deliveryservice.users.service;

import com.superprice.deliveryservice.users.model.User;
import com.superprice.deliveryservice.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//    
    public User createUser(User user) {
        // Encode the user's password before saving to the database
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User not found");
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public String generateVerificationToken(User user) {
        // Generate a unique verification token (e.g, UUID)
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userRepository.save(user);
        return token;
    }
    
    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null); // Return null if not found or handle it differently as needed
    }

    public void sendVerificationEmail(String userEmail) {
        User user = getUserByEmail(userEmail);
        if (user == null) {
            throw new IllegalArgumentException("User not found for email: " + userEmail);
        }

        String token = generateVerificationToken(user);
        String verificationUrl = "https://yourwebsite.com/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Email Verification");
        message.setText("Please click the following link to verify your email: " + verificationUrl);

        // Send the email
        javaMailSender.send(message);
    }

    
    //verify users
    public User getUserByVerificationToken(String token) {
        Optional<User> userOptional = userRepository.findByVerificationToken(token);
        return userOptional.orElse(null); // Return null if not found or handle it differently as needed
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
