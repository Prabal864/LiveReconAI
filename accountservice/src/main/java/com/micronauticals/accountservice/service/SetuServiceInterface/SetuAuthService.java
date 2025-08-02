package com.micronauticals.accountservice.service.SetuServiceInterface;

import com.micronauticals.accountservice.Dto.request.ConsentRequestDTO;
import com.micronauticals.accountservice.Dto.request.SetuLoginRequest;
import com.micronauticals.accountservice.Dto.response.consent.ConsentDataSessionResponseDTO;
import com.micronauticals.accountservice.Dto.response.consent.ConsentResponse;
import com.micronauticals.accountservice.Dto.response.consent.ConsentStatusResponseDTO;
import com.micronauticals.accountservice.Dto.response.consent.RevokeConsentResponse;
import com.micronauticals.accountservice.Dto.response.financialdata.FIPResponseDTO;
import com.micronauticals.accountservice.Dto.response.financialdata.SetuLoginResponse;
import reactor.core.publisher.Mono;

public interface SetuAuthService {
    SetuLoginResponse login(SetuLoginRequest request);
    Mono<ConsentResponse> createConsent(ConsentRequestDTO request);
    Mono<ConsentStatusResponseDTO> getConsentStatus(String consentId, boolean expanded);
    Mono<ConsentDataSessionResponseDTO> getDataSessionByConsentId(String consentId);
    Mono<FIPResponseDTO> getFiData(String sessionId);
    Mono<RevokeConsentResponse> revokeConsent(String consentID);
}
