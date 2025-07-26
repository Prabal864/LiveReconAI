package com.micronauticals.accountservice.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "fi_account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "masked_acc_number")
    private String maskedAccNumber;

    private String linkRefNumber;

    private String fistatus;

    @OneToMany(mappedBy = "fiAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fip_id")
    private Fip fip;

    // Nested embeddable for all account details
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "account.type", column = @Column(name = "account_type")),
            @AttributeOverride(name = "account.version", column = @Column(name = "account_version")),
            @AttributeOverride(name = "account.maskedAccNumber", column = @Column(name = "account_detail_masked_acc_number"))
    })
    private AccountData data;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountData {
        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "type", column = @Column(name = "account_type")),
                @AttributeOverride(name = "version", column = @Column(name = "account_version")),
                @AttributeOverride(name = "maskedAccNumber", column = @Column(name = "account_detail_masked_acc_number"))
        })
        private AccountDetail account;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountDetail {
        private String linkedAccRef;

        @Column(name = "account_detail_masked_acc_number")
        private String maskedAccNumber;

        @Column(name = "account_type")
        private String type;

        @Column(name = "account_version")
        private String version;

        @Embedded
        private Profile profile;

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "status", column = @Column(name = "summary_status")),
                @AttributeOverride(name = "type", column = @Column(name = "summary_type"))
        })
        private Summary summary;
        // NO transactions here
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Profile {
        @Embedded
        private Holders holders;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Holders {
        @Column(name = "holders_type")
        private String type;

        @ElementCollection
        @CollectionTable(name = "account_holder", joinColumns = @JoinColumn(name = "fi_account_id"))
        private List<Holder> holder;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Holder {
        private String address;
        private String ckycCompliance;
        private String dob;
        private String email;
        private String mobile;
        private String name;
        private String nominee;
        private String pan;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Summary {
        private String balanceDateTime;
        private String branch;
        private String currency;
        private String currentBalance;
        private String currentODLimit;
        private String drawingLimit;
        private String facility;
        private String ifscCode;
        private String micrCode;
        private String openingDate;

        @Embedded
        private Pending pending;

        @Column(name = "summary_status")
        private String status;

        @Column(name = "summary_type")
        private String type;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pending {
        private String amount;
        private String transactionType;
    }
}