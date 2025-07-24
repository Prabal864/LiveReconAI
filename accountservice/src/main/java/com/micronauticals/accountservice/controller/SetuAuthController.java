package com.micronauticals.accountservice.controller;


import com.micronauticals.accountservice.Dto.ConsentRequestDTO;
import com.micronauticals.accountservice.Dto.ConsentResponse;
import com.micronauticals.accountservice.Dto.SetuLoginRequest;
import com.micronauticals.accountservice.Dto.SetuLoginResponse;
import com.micronauticals.accountservice.service.SetuServiceInterface.SetuAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
}