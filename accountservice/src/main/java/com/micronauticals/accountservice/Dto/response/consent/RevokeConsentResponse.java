package com.micronauticals.accountservice.Dto.response.consent;

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
