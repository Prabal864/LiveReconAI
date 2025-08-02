package com.micronauticals.accountservice.Dto.response.consent;

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
public class ConsentDataSessionResponseDTO {
    private String consentId;
    private List<DataSession> dataSessions;
    private String traceId;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataSession {
        private String sessionId;
        private String status;
        private LocalDateTime created_at;
    }
}
