package com.micronauticals.transactionservice.dto.consent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsentStatusResponseDTO {

    private String id;
    private String url;
    private String status;
    private List<AccountsLinked> accountsLinked;
    private Detail detail;
    private LocalDateTime consentExpiry;
    private LocalDateTime consentStart;
    private String fetchType;
    private Frequency frequency;
    private List<DataFilter> dataFilter;
    private Purpose purpose;
    private Category category;
    private List<String> consentTypes;
    private DataRange dataRange;
    private List<String> fiTypes;
    private String vua;
    private DataLife dataLife;
    private String consentMode;
    private String redirectUrl;
    private List<Context> context;
    private String PAN;
    private Usage usage;
    private List<String> tags;
    private Boolean enableAdditionalPhoneNumber;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountsLinked {
        private String fipId;
        private String accType;
        private String fiType;
        private String maskedAccNumber;
        private String linkRefNumber;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        private String idType;
        private String idValue;
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
    public static class DataFilter {
        private String operator;
        private String type;
        private String value;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Purpose {
        private String code;
        private String text;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {
        private String type;
        private String refUri;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataRange {
        private LocalDateTime from;
        private LocalDateTime to;
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
    public static class Context {
        private String key;
        private String value;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {
        private LocalDateTime lastUsed;
        private Integer count;
    }
}
