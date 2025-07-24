package com.micronauticals.accountservice.service.SetuServiceInterface;

import com.micronauticals.accountservice.Dto.*;
import reactor.core.publisher.Mono;

public interface SetuAuthService {
    SetuLoginResponse login(SetuLoginRequest request);
    Mono<ConsentResponse> createConsent(ConsentRequestDTO request);
    Mono<ConsentStatusResponseDTO> getConsentStatus(String consentId,boolean expanded);
    Mono<ConsentDataSessionResponseDTO> getDataSessionByConsentId(String consentId);
    Mono<FinancialDataFetchResponseDTO> getFiData(String sessionId);
}
