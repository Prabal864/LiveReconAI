package com.micronauticals.accountservice.mapper;

import com.micronauticals.accountservice.Dto.response.financialdata.FIPResponseDTO;
import com.micronauticals.accountservice.entity.financialdata.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class TransactionMapper {

    private static final Logger log = LoggerFactory.getLogger(TransactionMapper.class);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${app.user.id:Prabal864}")
    private String defaultUserId;

    /**
     * Map a single transaction DTO to a DynamoDB Transaction entity
     */
    public Transaction mapToDynamoDbTransaction(
            FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Transactions.Transaction dto,
            String accountNumber,
            String consentId,
            Long fiAccountId) {

        if (dto == null) {
            log.warn("Attempted to map null transaction DTO to DynamoDB, user: {}", defaultUserId);
            return null;
        }

        String timestamp = LocalDateTime.now().format(timestampFormatter);
        String date = LocalDateTime.now().format(dateFormatter);
        String txnId = dto.getTxnId() != null ? dto.getTxnId() :
                "txn-" + System.currentTimeMillis();

        log.debug("Mapping transaction {} to DynamoDB format, user: {}", txnId, defaultUserId);

        // Create DynamoDB transaction entity
        return Transaction.builder()
                // Primary key components
                .pk("ACCOUNT#" + accountNumber)
                .sk("TXN#" + txnId)

                // Transaction details
                .txnId(txnId)
                .accountNumber(accountNumber)
                .consentId(consentId)
                .amount(parseDouble(dto.getAmount()))
                .currentBalance(parseDouble(dto.getCurrentBalance()))
                .mode(dto.getMode())
                .narration(dto.getNarration())
                .reference(dto.getReference())
                .transactionTimestamp(dto.getTransactionTimestamp())
                .type(dto.getType())
                .valueDate(dto.getValueDate())

                // FiAccount relationship - only ID as requested
                .fiAccountId(fiAccountId)

                // Metadata
                .userId(defaultUserId)
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();
    }

    /**
     * Map a list of transaction DTOs to DynamoDB Transaction entities
     */
    public List<Transaction> mapToDynamoDbTransactions(
            List<FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Transactions.Transaction> dtos,
            String accountNumber,
            String consentId,
            Long fiAccountId) {

        if (dtos == null || dtos.isEmpty()) {
            log.info("No transaction DTOs to map to DynamoDB, user: {}", defaultUserId);
            return new ArrayList<>();
        }

        log.info("Mapping {} transactions to DynamoDB format for account {}, user: {}",
                dtos.size(), accountNumber, defaultUserId);

        return dtos.stream()
                .map(dto -> mapToDynamoDbTransaction(dto, accountNumber, consentId, fiAccountId))
                .filter(t -> t != null)
                .collect(Collectors.toList());
    }

    /**
     * Convert from Transaction to a DTO representation (for API responses)
     */
    public FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Transactions.Transaction mapToDto(
            Transaction entity) {

        if (entity == null) return null;

        return FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Transactions.Transaction.builder()
                .txnId(entity.getTxnId())
                .amount(entity.getAmount() != null ? entity.getAmount().toString() : null)
                .currentBalance(entity.getCurrentBalance() != null ? entity.getCurrentBalance().toString() : null)
                .mode(entity.getMode())
                .narration(entity.getNarration())
                .reference(entity.getReference())
                .transactionTimestamp(entity.getTransactionTimestamp())
                .type(entity.getType())
                .valueDate(entity.getValueDate())
                .build();
    }

    /**
     * Convert a list of Transaction entities to DTOs
     */
    public List<FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Transactions.Transaction> mapToDtos(
            List<Transaction> entities) {

        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(this::mapToDto)
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    private Double parseDouble(String val) {
        try {
            return val != null ? Double.valueOf(val) : null;
        } catch (Exception e) {
            log.warn("Error parsing double value: {}, user: {}", val, defaultUserId);
            return null;
        }
    }
}