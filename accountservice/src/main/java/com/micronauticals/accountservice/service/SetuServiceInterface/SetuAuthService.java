package com.micronauticals.accountservice.service.SetuServiceInterface;

import com.micronauticals.accountservice.Dto.ConsentRequestDTO;
import com.micronauticals.accountservice.Dto.ConsentResponse;
import com.micronauticals.accountservice.Dto.SetuLoginRequest;
import com.micronauticals.accountservice.Dto.SetuLoginResponse;
import reactor.core.publisher.Mono;

public interface SetuAuthService {
    SetuLoginResponse login(SetuLoginRequest request);
    Mono<ConsentResponse> createConsent(ConsentRequestDTO request);
}
