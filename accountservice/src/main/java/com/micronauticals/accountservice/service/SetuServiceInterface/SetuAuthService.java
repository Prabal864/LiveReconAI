package com.micronauticals.accountservice.service.SetuServiceInterface;

import com.micronauticals.accountservice.Dto.request.ConsentRequestDTO;
import com.micronauticals.accountservice.Dto.request.SetuLoginRequest;
import com.micronauticals.accountservice.Dto.response.*;
import reactor.core.publisher.Mono;

public interface SetuAuthService {
    SetuLoginResponse login(SetuLoginRequest request);
    Mono<ConsentResponse> createConsent(ConsentRequestDTO request);
    Mono<ConsentStatusResponseDTO> getConsentStatus(String consentId, boolean expanded);
    Mono<ConsentDataSessionResponseDTO> getDataSessionByConsentId(String consentId);
    Mono<FinancialDataFetchResponseDTO> getFiData(String sessionId);
    Mono<RevokeConsentResponse> revokeConsent(String consentID);
}
