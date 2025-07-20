package com.micronauticals.authservices.controller;

import com.micronauticals.authservices.dto.*;
import com.micronauticals.authservices.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(userService.refreshToken(refreshTokenRequest.getRefreshToken()));
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(Authentication authentication) {
        return ResponseEntity.ok(authentication != null && authentication.isAuthenticated());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.getUserProfile(authentication.getName()));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateUserProfile(
            Authentication authentication,
            @Valid @RequestBody ProfileUpdateRequest updateRequest) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.updateUserProfile(authentication.getName(), updateRequest));
    }

    @PostMapping("/internal/verify")
    public ResponseEntity<Map<String, Object>> verifyTokenInternal(
            @RequestHeader("Authorization") 
            @NotBlank(message = "Authorization header is required")
            String authHeader) {
        
        if (!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        
        if (token.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(userService.verifyTokenForInternalService(token));
    }

    @GetMapping("/internal/user/phone/{phoneNumber}")
    public ResponseEntity<UserDto> getUserByPhoneNumber(
            @PathVariable 
            @NotBlank(message = "Phone number is required")
            @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
            String phoneNumber) {
        
        return userService.findUserByPhoneNumber(phoneNumber)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}