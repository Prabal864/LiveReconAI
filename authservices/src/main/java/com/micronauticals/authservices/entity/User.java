package com.micronauticals.authservices.entity;

import com.micronauticals.authservices.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Embedded
    private Consent consent;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Embeddable
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Consent{

        @ElementCollection
        @CollectionTable(name = "consent_sessions", joinColumns = @JoinColumn(name = "user_id"))
        private List<DataSessions> sessions;
        private String traceId;

        @Column(name = "consent_id")
        private String consentId;

        @Embeddable
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DataSessions{
            private String sessionId;
            private String status;
            private String createdAt;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}