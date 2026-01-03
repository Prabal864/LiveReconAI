package com.micronauticals.transactionservice.dto.consent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevokeConsentResponse {
    private String status;
    private String traceId;
}
