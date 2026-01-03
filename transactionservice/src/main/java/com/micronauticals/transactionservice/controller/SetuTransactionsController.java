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

import java.util.List;

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

    @GetMapping("/{sessionId}/getFiData")
    public Mono<ResponseEntity<FIPResponseDTO>> getFiDataBySessionId(
            @PathVariable String sessionId, @RequestHeader("Authorization") String authorization ) {

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

