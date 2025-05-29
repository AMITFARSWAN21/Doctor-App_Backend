package com.scm.controllers;

import com.scm.model.User;
import com.scm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allow frontend access
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new user
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            return "Email already in use!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    // Login user
    @PostMapping("/login")
    public Object login(@RequestBody User loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

        if (userOpt.isEmpty()) {
            return "Invalid email or password!";
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return "Invalid email or password!";
        }

        // return basic user info (excluding password)
        return new AuthResponse(user.getName(), user.getEmail(), user.getRole());
    }

    // DTO for login response
    record AuthResponse(String name, String email, String role) {
    }
}
