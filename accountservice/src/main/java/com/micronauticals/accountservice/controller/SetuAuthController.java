package com.micronauticals.accountservice.controller;


import com.micronauticals.accountservice.Dto.request.ConsentRequestDTO;
import com.micronauticals.accountservice.Dto.request.SetuLoginRequest;
import com.micronauticals.accountservice.Dto.response.consent.ConsentDataSessionResponseDTO;
import com.micronauticals.accountservice.Dto.response.consent.ConsentResponse;
import com.micronauticals.accountservice.Dto.response.consent.ConsentStatusResponseDTO;
import com.micronauticals.accountservice.Dto.response.consent.RevokeConsentResponse;
import com.micronauticals.accountservice.Dto.response.financialdata.DataRefreshPull;
import com.micronauticals.accountservice.Dto.response.financialdata.FIPResponseDTO;
import com.micronauticals.accountservice.Dto.response.financialdata.SetuLoginResponse;
import com.micronauticals.accountservice.service.SetuServiceInterface.SetuAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/setu/auth")
@RequiredArgsConstructor
public class SetuAuthController {

    private final SetuAuthService setuAuthService;

    @PostMapping("/login")
    public ResponseEntity<SetuLoginResponse> login(@RequestBody SetuLoginRequest request) {
        SetuLoginResponse response = setuAuthService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/consent")
    public Mono<ResponseEntity<ConsentResponse>> createConsent(@RequestBody ConsentRequestDTO consentRequestDTO) {
        return setuAuthService.createConsent(consentRequestDTO)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).build()));
    }

    @GetMapping("/{consentId}/status")
    public Mono<ResponseEntity<ConsentStatusResponseDTO>> getConsentStatus(
            @PathVariable String consentId,
            @RequestParam(defaultValue = "false") boolean expanded) {

        return setuAuthService.getConsentStatus(consentId, expanded)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    // Optionally log the error here
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    @GetMapping("/{consentId}/consentDataSession")
    public Mono<ResponseEntity<ConsentDataSessionResponseDTO>> getConsentDataSessions(
            @PathVariable String consentId) {

        return setuAuthService.getDataSessionByConsentId(consentId)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    // Optionally log the error here
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }


    @GetMapping("/{sessionId}/getFiData")
    public Mono<ResponseEntity<FIPResponseDTO>> getFiDataBySessionId(
            @PathVariable String sessionId) {

        return setuAuthService.getFiData(sessionId)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    // Optionally log the error here
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    @PostMapping("/{consentID}/revokeConsent")
    public Mono<ResponseEntity<RevokeConsentResponse>> revokeConsentByConsentID(
            @PathVariable String consentID) {
        return setuAuthService.revokeConsent(consentID)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
    }

    @PostMapping("{sessionID}/refreshDataPull")
    public Mono<ResponseEntity<DataRefreshPull>> getRefreshDataBySessionID(@PathVariable String sessionID, @RequestParam(required = false,name = "restart",defaultValue = "false") boolean restart){
        return setuAuthService.refreshDataPull(sessionID, restart)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
    }
}