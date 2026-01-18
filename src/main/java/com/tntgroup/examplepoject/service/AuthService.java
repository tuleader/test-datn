package com.tntgroup.examplepoject.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tntgroup.examplepoject.dto.AuthResponse;
import com.tntgroup.examplepoject.dto.LoginRequest;
import com.tntgroup.examplepoject.dto.RegisterRequest;
import com.tntgroup.examplepoject.entity.User;
import com.tntgroup.examplepoject.repository.UserRepository;
import com.tntgroup.examplepoject.utils.ValidationUtils;

import lombok.RequiredArgsConstructor;

/**
 * Authentication service for login/register.
 * Uses ValidationUtils directly for validation (no wrapper service).
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        // Validate input using ValidationUtils directly
        if (!ValidationUtils.isValidEmail(request.getEmail())) {
            throw new RuntimeException("Invalid email format");
        }
        if (!ValidationUtils.isValidPassword(request.getPassword())) {
            throw new RuntimeException("Password must be at least 8 characters with uppercase, lowercase, and digit");
        }
        if (!ValidationUtils.isValidUsername(request.getUsername())) {
            throw new RuntimeException("Username must be 3-20 characters, alphanumeric only");
        }

        // Check duplicates
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        // Generate token
        String token = jwtService.generateToken(user.getUsername());

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .message("Registration successful")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getUsername());

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .message("Login successful")
                .build();
    }
}
