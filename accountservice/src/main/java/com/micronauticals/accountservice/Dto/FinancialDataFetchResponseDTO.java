package com.micronauticals.accountservice.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinancialDataFetchResponseDTO {
    private List<Fip> fips;
    private String status; // ACTIVE, PENDING, COMPLETED, EXPIRED, FAILED, PARTIAL
    private String consentId;
    private DataRange dataRange;
    private String id;
    private String format; // xml or json

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fip {
        private String fipID;
        private List<Account> accounts;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Account {
        private String maskedAccNumber;
        private Object data; // You can replace Object with a specific class if the structure is known
        private String linkRefNumber;
        private String FIstatus;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataRange {
        private LocalDateTime to;
        private LocalDateTime from;
    }
}

