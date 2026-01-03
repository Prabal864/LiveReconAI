package com.micronauticals.authservices.config;

import com.micronauticals.authservices.entity.User;
import com.micronauticals.authservices.enums.UserRole;
import com.micronauticals.authservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeDefaultUsers() {
        return args -> {
            log.info("Starting default user initialization...");

            // Create default admin user
            if (!userRepository.existsByUsername("admin")) {
                User admin = User.builder()
                        .username("admin")
                        .email("admin@livereconai.com")
                        .password(passwordEncoder.encode("admin"))
                        .firstName("Admin")
                        .lastName("User")
                        .phoneNumber("+1234567890")
                        .role(UserRole.ADMIN)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                userRepository.save(admin);
                log.info("✓ Default admin user created successfully - username: admin, password: admin");
            } else {
                log.info("✓ Admin user already exists");
            }

            // Create default regular user
            if (!userRepository.existsByUsername("user")) {
                User user = User.builder()
                        .username("user")
                        .email("user@livereconai.com")
                        .password(passwordEncoder.encode("user"))
                        .firstName("Regular")
                        .lastName("User")
                        .phoneNumber("+0987654321")
                        .role(UserRole.USER)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                userRepository.save(user);
                log.info("✓ Default user created successfully - username: user, password: user");
            } else {
                log.info("✓ Regular user already exists");
            }

            log.info("Default user initialization completed!");
        };
    }
}

