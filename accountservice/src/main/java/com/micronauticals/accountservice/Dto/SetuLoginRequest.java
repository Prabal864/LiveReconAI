package com.micronauticals.accountservice.Dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetuLoginRequest {
    private String grant_type;
    private String clientID;
    private String secret;
}