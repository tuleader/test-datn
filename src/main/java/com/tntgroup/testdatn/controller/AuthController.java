package com.tntgroup.testdatn.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tntgroup.testdatn.dto.AuthResponse;
import com.tntgroup.testdatn.dto.LoginRequest;
import com.tntgroup.testdatn.dto.RegisterRequest;
import com.tntgroup.testdatn.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Authentication REST Controller.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Login, Register, and Logout APIs")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Create a new user account with username, email, and password")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(AuthResponse.builder().message(e.getMessage()).build());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login with username and password, returns JWT token")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(AuthResponse.builder().message(e.getMessage()).build());
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Logout (client should discard the token)")
    public ResponseEntity<Map<String, String>> logout() {
        // JWT tokens are stateless - logout is handled client-side
        return ResponseEntity.ok(Map.of("message", "Logged out successfully. Please discard your token."));
    }
}
