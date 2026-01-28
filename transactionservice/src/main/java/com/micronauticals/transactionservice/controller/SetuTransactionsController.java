package com.micronauticals.transactionservice.controller;

import com.micronauticals.transactionservice.dto.FIPResponseDTO;
import com.micronauticals.transactionservice.entity.financialdata.Transaction;
import com.micronauticals.transactionservice.repository.TransactionRepository;
import com.micronauticals.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/setu/transaction")
@RequiredArgsConstructor
public class SetuTransactionsController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    @GetMapping("/byConsentID")
    public ResponseEntity<List<Transaction>> getTransactionsByConsentId(@RequestParam String consentId) {
        log.info("Fetching transactions for consent ID: {}", consentId);
        List<Transaction> transactions = transactionRepository.findByConsentId(consentId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{consentId}/ingestData")
    public Mono<ResponseEntity<Map<String, Object>>> ingestTransactionsByConsentId(@PathVariable String consentId) {
        log.info("Ingesting transactions to RAG for consent ID: {}", consentId);
        List<Transaction> transactions = transactionRepository.findByConsentId(consentId);

        if (transactions.isEmpty()) {
            log.warn("No transactions found for consentId: {}", consentId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "No transactions found for consentId: " + consentId);
            response.put("consentId", consentId);
            response.put("transactionsIngested", 0);
            response.put("timestamp", LocalDateTime.now().toString());
            return Mono.just(ResponseEntity.ok(response));
        }

        log.info("Found {} transactions, sending to RAG service...", transactions.size());

        // Wait for RAG service call to complete and return result
        return transactionService.sendTransactionsToRagService(transactions)
                .then(Mono.fromCallable(() -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "success");
                    response.put("message", "Successfully ingested transactions to RAG service");
                    response.put("consentId", consentId);
                    response.put("transactionsIngested", transactions.size());
                    response.put("timestamp", LocalDateTime.now().toString());
                    log.info("✅ Successfully sent {} transactions to RAG service for consentId: {}", transactions.size(), consentId);
                    return ResponseEntity.ok(response);
                }))
                .onErrorResume(error -> {
                    log.error("❌ Failed to send transactions to RAG service for consentId: {}, error: {}",
                            consentId, error.getMessage(), error);
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "error");
                    response.put("message", "Failed to ingest transactions: " + error.getMessage());
                    response.put("consentId", consentId);
                    response.put("transactionsIngested", 0);
                    response.put("timestamp", LocalDateTime.now().toString());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
                });
    }

    /**
     * @deprecated Use AccountService endpoint instead: GET /api/setu/auth/{sessionId}/getFiData
     * This endpoint is deprecated to prevent duplicate data fetching and inconsistent RAG calls.
     * TransactionService should only query existing transactions, not fetch new data.
     */
    @Deprecated
    @GetMapping("/{sessionId}/getFiData")
    public Mono<ResponseEntity<FIPResponseDTO>> getFiDataBySessionId(
            @PathVariable String sessionId, @RequestHeader("Authorization") String authorization ) {

        log.warn("⚠️ DEPRECATED: getFiData called on TransactionService. Use AccountService instead: GET http://localhost:8072/api/setu/auth/{}/getFiData", sessionId);
        log.warn("⚠️ This endpoint will be removed in a future version. Please update your client to use AccountService.");

        return transactionService.getFiData(sessionId, authorization.substring(7))
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    // Optionally log the error here
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    @GetMapping("/accountNumber")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountNumber(@RequestParam String accountNumber) {
        List<Transaction> transactions = transactionRepository.findByAccountNumber(accountNumber);
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/delete-Transaction/accountNumber")
    public ResponseEntity<?> deleteTransactionByAccountNumber(@RequestParam String accountNumber) {
        log.info("Deleting transaction of account number: {}", accountNumber);
        transactionRepository.deleteByAccountNumber(accountNumber);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-Transaction/transactionId")
    public ResponseEntity<?> deleteTransactionByTransactionId(@RequestParam String accountNumber,
                                                              @RequestParam String transactionId) {
        log.info("Deleting transaction with transaction ID: {} ", transactionId);
        transactionRepository.deleteById(accountNumber, transactionId);
        return ResponseEntity.noContent().build();
    }
}

