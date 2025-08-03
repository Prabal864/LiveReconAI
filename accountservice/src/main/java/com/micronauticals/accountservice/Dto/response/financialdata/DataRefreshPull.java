package com.micronauticals.accountservice.Dto.response.financialdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataRefreshPull {
    private String message;
    private String status;
    private String traceId;
}
