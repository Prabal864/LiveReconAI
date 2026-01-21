package com.micronauticals.authservices.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SaveConsentRequest {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "consentId is required")
    private String consentId;
}
