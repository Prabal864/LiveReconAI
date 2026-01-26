package com.micronauticals.accountservice.Dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ConsentIdsRequest {
    @NotEmpty
    private List<String> consentIds;
}
