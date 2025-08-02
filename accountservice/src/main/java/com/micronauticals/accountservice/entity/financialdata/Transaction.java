package com.micronauticals.accountservice.entity.financialdata;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    private String txnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fi_account_id")
    private FiAccount fiAccount; // Link to respective account

    @Column(nullable = false)
    private Double amount;

    @Column
    private Double currentBalance;

    @Column
    private String mode;

    @Column(length = 1024)
    private String narration;

    @Column
    private String reference;

    @Column
    private LocalDateTime transactionTimestamp;

    @Column
    private String type; // CREDIT or DEBIT

    @Column
    private LocalDateTime valueDate;
}