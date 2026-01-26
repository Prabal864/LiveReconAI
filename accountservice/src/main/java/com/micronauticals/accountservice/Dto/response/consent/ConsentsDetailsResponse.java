package com.micronauticals.accountservice.Dto.response.consent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentsDetailsResponse {
    private List<ConsentDetailsResponse> consents;
}
