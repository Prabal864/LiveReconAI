package com.micronauticals.transactionservice.service.TransactionServiceInterface;

import com.micronauticals.transactionservice.dto.DataRefreshPull;
import com.micronauticals.transactionservice.dto.FIPResponseDTO;
import reactor.core.publisher.Mono;

public interface TransactionService {

        Mono<FIPResponseDTO> getFiData(String sessionId, String authorization);
        Mono<DataRefreshPull> refreshDataPull(String sessionID, boolean restart);
}
