package com.micronauticals.transactionservice.entity.financialdata.consent;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "consent_data_session")
public class ConsentDataSession {

    @Id
    private String consentId;

    @Column(nullable = true)
    private String traceId;

    @OneToMany(mappedBy = "consentDataSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DataSession> dataSessions;

    @Entity
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "data_session")
    public static class DataSession {

        @Id
        private String sessionId;

        private String status;

        private LocalDateTime created_at;

        @ManyToOne
        @JoinColumn(name = "consent_id")
        private ConsentDataSession consentDataSession;
    }
}
