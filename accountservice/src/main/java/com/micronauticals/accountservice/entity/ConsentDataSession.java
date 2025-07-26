package com.micronauticals.accountservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consent_data_sessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentDataSession {
    @Id
    private String sessionId;

    @Column(nullable = false)
    private String consentId;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}