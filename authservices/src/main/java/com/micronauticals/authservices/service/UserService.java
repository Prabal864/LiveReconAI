package com.micronauticals.authservices.service;

import com.micronauticals.authservices.dto.*;
import com.micronauticals.authservices.entity.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    AuthResponse registerUser(RegisterRequest registerRequest);
    AuthResponse authenticateUser(LoginRequest loginRequest );
    AuthResponse refreshToken(String refreshToken);
    boolean verifyToken(String token);
    UserProfileResponse getUserProfile(String username);
    UserProfileResponse updateUserProfile(String username, ProfileUpdateRequest updateRequest);
    Optional<User> findByUsername(String username);
    Optional<UserDto> findUserByPhoneNumber(String phoneNumber);
    Map<String, Object> verifyTokenForInternalService(String token);
}
