package com.micronauticals.accountservice.entity.consent;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "consents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consent {

    @Id
    private String id;

    @Column
    private String url;

    @Column
    private String status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fiTypes", column = @Column(name = "detail_fiTypes")),
            @AttributeOverride(name = "consentStart", column = @Column(name = "detail_consent_start")),
            @AttributeOverride(name = "fetchType", column = @Column(name = "detail_fetch_type")),
            @AttributeOverride(name = "vua", column = @Column(name = "detail_vua")),
            @AttributeOverride(name = "consentExpiry", column = @Column(name = "detail_consent_expiry")),
            @AttributeOverride(name = "consentMode", column = @Column(name = "detail_consent_mode"))
    })
    private Detail detail;

    @Column
    private String redirectUrl;

    @ElementCollection
    @CollectionTable(name = "consent_context", joinColumns = @JoinColumn(name = "consent_id"))
    private List<Context> context;

    @Column
    private String pan;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "count", column = @Column(name = "usage_count")),
            @AttributeOverride(name = "lastUsed", column = @Column(name = "usage_last_used"))
    })
    private Usage usage;

    @ElementCollection
    @CollectionTable(name = "consent_accounts_linked", joinColumns = @JoinColumn(name = "consent_id"))
    private List<AccountLinked> accountsLinked;

    @ElementCollection
    @CollectionTable(name = "consent_tags", joinColumns = @JoinColumn(name = "consent_id"))
    @Column(name = "tag")
    private List<String> tags;

    @Column
    private String traceId;

    @Column
    private Boolean enableAdditionalPhoneNumber;


    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        @ElementCollection
        @CollectionTable(name = "consent_detail_fitypes", joinColumns = @JoinColumn(name = "consent_id"))
        @Column(name = "fi_type")
        private List<String> fiTypes;

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "refUri", column = @Column(name = "purpose_ref_uri")),
                @AttributeOverride(name = "code", column = @Column(name = "purpose_code")),
                @AttributeOverride(name = "text", column = @Column(name = "purpose_text"))
        })
        private Purpose purpose;

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "unit", column = @Column(name = "data_life_unit")),
                @AttributeOverride(name = "value", column = @Column(name = "data_life_value"))
        })
        private DataLife dataLife;

        @Column
        private String consentStart;

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "unit", column = @Column(name = "frequency_unit")),
                @AttributeOverride(name = "value", column = @Column(name = "frequency_value"))
        })
        private Frequency frequency;

        @Column
        private String fetchType;

        @Column
        private String vua;

        @ElementCollection
        @CollectionTable(name = "consent_detail_consent_types", joinColumns = @JoinColumn(name = "consent_id"))
        @Column(name = "consent_type")
        private List<String> consentTypes;

        @Column
        private String consentExpiry;

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "from", column = @Column(name = "data_range_from")),
                @AttributeOverride(name = "to", column = @Column(name = "data_range_to"))
        })
        private DataRange dataRange;

        @Column
        private String consentMode;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Purpose {
        @Column
        private String refUri;

        @Embedded
        @AttributeOverride(name = "type", column = @Column(name = "category_type"))
        private Category category;

        @Column
        private String code;

        @Column
        private String text;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {
        @Column
        private String type;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataLife {
        @Column
        private String unit;
        @Column
        private Integer value;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Frequency {
        @Column
        private String unit;
        @Column
        private Integer value;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataRange {
        @Column
        private String from;
        @Column
        private String to;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Context {
        // Example fields, update as needed
        @Column(name = "context_key")
        private String key;
        @Column(name = "context_value")
        private String value;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {
        @Column
        private String count;
        @Column
        private String lastUsed;
    }

    @Embeddable
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class AccountLinked {
        @Column
        private String accountId;
        @Column
        private String maskedAccountNumber;
        @Column
        private String bankName;
    }
}