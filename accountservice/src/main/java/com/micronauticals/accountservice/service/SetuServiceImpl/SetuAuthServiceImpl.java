package com.micronauticals.accountservice.service.SetuServiceImpl;
import com.micronauticals.accountservice.Dto.request.ConsentRequestDTO;
import com.micronauticals.accountservice.Dto.request.SetuLoginRequest;
import com.micronauticals.accountservice.Dto.response.*;
import com.micronauticals.accountservice.exception.SetuLoginException;
import com.micronauticals.accountservice.service.SetuServiceInterface.SetuAuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.View;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class SetuAuthServiceImpl implements SetuAuthService {

    private final WebClient.Builder webClientBuilder;

    private static final String SETU_LOGIN_URL = "https://orgservice-prod.setu.co/v1/users/login";
    private static final String SETU_CONSENT_URL = "https://fiu-sandbox.setu.co/v2/consents";
    private static final Logger log = LoggerFactory.getLogger(SetuAuthServiceImpl.class);
    private final View error;

    private String accessToken;
    private String refreshToken;

    @Override
    public SetuLoginResponse login(SetuLoginRequest request) {
        try {
            WebClient webClient = webClientBuilder.build();

            Mono<Map> responseMono = webClient.post()
                    .uri(SETU_LOGIN_URL)
                    .header("client", "bridge")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(Map.of(
                            "grant_type", request.getGrant_type(),
                            "clientID", request.getClientID(),
                            "secret", request.getSecret()
                    ))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse -> {
                        HttpStatusCode statusCode = clientResponse.statusCode();
                        return clientResponse.bodyToMono(String.class)
                                .onErrorReturn("Unable to read error response")
                                .timeout(Duration.ofSeconds(5))
                                .flatMap(errorBody -> {
                                    String sanitizedError = sanitizeErrorMessage(errorBody);
                                    log.error("Setu API error - Status: {}, Response: {}", statusCode, sanitizedError);
                                    return Mono.error(new SetuLoginException("Setu login failed with status " + statusCode.value()));
                                });
                    })
                    .bodyToMono(Map.class);

            Map<String, Object> result = responseMono.block();

            if (result == null || !result.containsKey("access_token")) {
                throw new SetuLoginException("Missing access token in Setu response");
            }

            this.accessToken = (String) result.get("access_token"); // ✅ store token
            this.refreshToken = (String) result.get("refresh_token");

            log.info("Received access token from Setu");

            return SetuLoginResponse.builder()
                    .accessToken(this.accessToken)
                    .refreshToken(this.refreshToken)
                    .build();

        } catch (Exception ex) {
            log.error("Error during Setu login", ex);
            throw new SetuLoginException("Error during Setu login: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Mono<ConsentResponse> createConsent(ConsentRequestDTO requestDTO) {
        if (accessToken == null) {
            return Mono.error(new SetuLoginException("Access token not available. Please login first."));
        }
        WebClient webClient = webClientBuilder.build();
        return webClient.post()
                .uri(SETU_CONSENT_URL)
                .header(HttpHeaders.AUTHORIZATION, STR."Bearer \{accessToken}")
                .header("x-product-instance-id", "681c4095-7cb7-402b-9f48-5c747c01cf95")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), response -> {
                    log.error("Failed to create consent. HTTP Status: {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error body: {}", errorBody);
                                return Mono.error(new RuntimeException("Error creating consent: " + errorBody));
                            });
                })
                .bodyToMono(ConsentResponse.class)
                .doOnNext(response -> log.info("Consent created successfully: {}", response))
                .doOnError(error -> log.error("Error occurred during consent creation", error));

    }

    @Override
    public Mono<ConsentStatusResponseDTO> getConsentStatus(String consentId, boolean expanded) {
        if (accessToken == null) {
            return Mono.error(new SetuLoginException("Access token not available. Please login first."));
        }

        WebClient webClient = webClientBuilder.build();

        String url = STR."https://fiu-sandbox.setu.co/v2/consents/\{consentId}?expanded=\{expanded}";

        return webClient.get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, STR."Bearer \{accessToken}")
                .header("x-product-instance-id", "681c4095-7cb7-402b-9f48-5c747c01cf95")
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), response -> {
                    log.error("Failed to fetch consent status. HTTP Status: {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error body: {}", errorBody);
                                return Mono.error(new RuntimeException("Error fetching consent status: " + errorBody));
                            });
                })
                .bodyToMono(ConsentStatusResponseDTO.class)
                .doOnNext(response -> log.info("Consent status fetched successfully: {}", response))
                .doOnError(error -> log.error("Error occurred while fetching consent status", error));
    }

    @Override
    public Mono<ConsentDataSessionResponseDTO> getDataSessionByConsentId(String consentId){

        if (accessToken == null) {
            return Mono.error(new SetuLoginException("Access token not available. Please login first."));
        }

        WebClient webClient = webClientBuilder.build();

        String url = STR."https://fiu-sandbox.setu.co/v2/consents/\{consentId}/data-sessions";

        return webClient.get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, STR."Bearer \{accessToken}")
                .header("x-product-instance-id", "681c4095-7cb7-402b-9f48-5c747c01cf95")
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), response -> {
                    log.error("Failed to fetch consent status. HTTP Status: {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error body: {}", errorBody);
                                return Mono.error(new RuntimeException("Error fetching consent status: " + errorBody));
                            });
                })
                .bodyToMono(ConsentDataSessionResponseDTO.class)
                .doOnNext(response -> log.info("Consent status fetched successfully: {}", response))
                .doOnError(error -> log.error("Error occurred while fetching consent status", error));

    }

    @Override
    public Mono<FinancialDataFetchResponseDTO> getFiData(String sessionId){

        if (accessToken == null) {
            return Mono.error(new SetuLoginException("Access token not available. Please login first."));
        }

        WebClient webClient = webClientBuilder.build();

        String url = STR."https://fiu-sandbox.setu.co/v2/sessions/\{sessionId}";

        return webClient.get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, STR."Bearer \{accessToken}")
                .header("x-product-instance-id", "681c4095-7cb7-402b-9f48-5c747c01cf95")
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), response -> {
                    log.error("Failed to fetch consent status. HTTP Status: {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error body: {}", errorBody);
                                return Mono.error(new RuntimeException("Error fetching consent status: " + errorBody));
                            });
                })
                .bodyToMono(FinancialDataFetchResponseDTO.class)
                .doOnNext(response -> log.info("Consent status fetched successfully: {}", response))
                .doOnError(error -> log.error("Error occurred while fetching consent status", error));

    }

    public Mono<RevokeConsentResponse> revokeConsent(String consentID){
        WebClient webClient = webClientBuilder.build();
        String url = STR."https://fiu-sandbox.setu.co/v2/consents/\{consentID}/revoke";
        return webClient.post()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, STR."Bearer \{accessToken}")
                .header("x-product-instance-id", "681c4095-7cb7-402b-9f48-5c747c01cf95")
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), response -> {
                    log.error("Failed to revoke consent. HTTP Status: {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error body: {}", errorBody);
                                return Mono.error(new RuntimeException("Error fetching consent status: " + errorBody));
                            });
                })
                .bodyToMono(RevokeConsentResponse.class)
                .doOnNext(response -> log.info("Consent revoked successfully: {}", response))
                .doOnError(error -> log.error("Error occurred while fetching consent status", error));
    }


    private String sanitizeErrorMessage(String errorBody) {
        if (errorBody == null || errorBody.trim().isEmpty()) {
            return "Empty error response";
        }
        return errorBody.length() > 200 ? errorBody.substring(0, 200) + "..." : errorBody;
    }
}
