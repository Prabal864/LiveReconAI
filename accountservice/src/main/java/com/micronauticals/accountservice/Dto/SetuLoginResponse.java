package com.micronauticals.accountservice.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetuLoginResponse {
    private String accessToken;
    private String refreshToken;
}