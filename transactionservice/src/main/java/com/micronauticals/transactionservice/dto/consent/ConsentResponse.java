package com.micronauticals.transactionservice.dto.consent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsentResponse {
    private String id;
    private String url;
    private String status;
    private Detail detail;
    private String redirectUrl;
    private List<Context> context;
    private String pan;
    private Usage usage;
    private List<AccountLinked> accountsLinked;
    private List<String> tags;
    private String traceId;
    private Boolean enableAdditionalPhoneNumber;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        private List<String> fiTypes;
        private Purpose purpose;
        private DataLife dataLife;
        private String consentStart;
        private Frequency frequency;
        private String fetchType;
        private String vua;
        private List<String> consentTypes;
        private String consentExpiry;
        private DataRange dataRange;
        private String consentMode;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Purpose {
        private String refUri;
        private Category category;
        private String code;
        private String text;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {
        private String type;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataLife {
        private String unit;
        private Integer value;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Frequency {
        private String unit;
        private Integer value;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataRange {
        private String from;
        private String to;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Context {
        private String key;
        private String value;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {
        private String count;
        private String lastUsed;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountLinked {
        private String accountId;
        private String maskedAccountNumber;
        private String bankName;
    }
}
