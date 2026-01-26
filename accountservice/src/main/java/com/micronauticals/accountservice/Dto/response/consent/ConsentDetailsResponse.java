package com.micronauticals.accountservice.Dto.response.consent;

import com.micronauticals.accountservice.entity.consent.Consent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentDetailsResponse {
    private String consentId;

    /** Persisted consent snapshot in accountservice DB (if present). */
    private Consent persisted;

    /** Live status from Setu. */
    private ConsentStatusResponseDTO status;

    /** Data sessions from Setu. */
    private ConsentDataSessionResponseDTO dataSessions;
}
