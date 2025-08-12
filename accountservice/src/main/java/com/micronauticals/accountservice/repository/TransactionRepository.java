package com.micronauticals.accountservice.repository;

import com.micronauticals.accountservice.entity.financialdata.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Transaction entities in DynamoDB
 *
 * Current Date and Time (UTC - YYYY-MM-DD HH:MM:SS formatted): 2025-08-12 12:59:39
 * Current User's Login: Prabal864
 */
@Repository
public class TransactionRepository {

    private static final Logger log = LoggerFactory.getLogger(TransactionRepository.class);
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbTable<Transaction> transactionTable;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${app.user.id:Prabal864}")
    private String defaultUserId;

    @Autowired
    public TransactionRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbEnhancedClient dynamoDbClient,
                                 @Value("${aws.dynamodb.table.name:Transaction_Account_Service}") String tableName) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.transactionTable = dynamoDbClient.table(tableName,
                TableSchema.fromBean(Transaction.class));
        log.info("Initialized TransactionRepository with DynamoDB table '{}' at {}, user: {}",
                tableName, LocalDateTime.now().format(formatter), defaultUserId);
    }

    /**
     * Save a single transaction to DynamoDB
     */
    public Transaction save(Transaction transaction) {
        log.info("Saving transaction {} to DynamoDB, user: {}",
                transaction.getTxnId(), defaultUserId);
        transactionTable.putItem(transaction);
        return transaction;
    }

    /**
     * Save multiple transactions to DynamoDB
     */
    public List<Transaction> saveAll(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            log.info("No transactions to save, user: {}", defaultUserId);
            return transactions;
        }

        log.info("Batch saving {} transactions to DynamoDB, user: {}",
                transactions.size(), defaultUserId);

        // DynamoDB BatchWriteItem has a limit of 25 items per request
        int batchSize = 25;
        int totalBatches = (int) Math.ceil((double) transactions.size() / batchSize);

        for (int batchNum = 0; batchNum < totalBatches; batchNum++) {
            int fromIndex = batchNum * batchSize;
            int toIndex = Math.min(fromIndex + batchSize, transactions.size());
            List<Transaction> batch = transactions.subList(fromIndex, toIndex);

            try {
                // Create a write batch for this chunk
                WriteBatch.Builder<Transaction> writeBuilder = WriteBatch.builder(Transaction.class)
                        .mappedTableResource(transactionTable);

                // Add each transaction to the batch
                for (Transaction txn : batch) {
                    writeBuilder.addPutItem(txn);
                }

                // Execute the batch write
                BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder()
                        .writeBatches(writeBuilder.build())
                        .build();

                log.info("Executing batch write for batch {}/{} ({} items), user: {}",
                        batchNum + 1, totalBatches, batch.size(), defaultUserId);

                dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest);

                log.info("Successfully wrote batch {}/{}, user: {}",
                        batchNum + 1, totalBatches, defaultUserId);

            } catch (Exception e) {
                log.error("Error writing batch {}/{}, user: {}, error: {}",
                        batchNum + 1, totalBatches, defaultUserId, e.getMessage(), e);

                // Continue with other batches even if one fails
                continue;
            }
        }

        log.info("Completed batch saving of {} transactions, user: {}",
                transactions.size(), defaultUserId);

        return transactions;
    }

    /**
     * Find a transaction by account number and transaction ID
     */
    public Optional<Transaction> findById(String accountNumber, String transactionId) {
        Key key = Key.builder()
                .partitionValue("ACCOUNT#" + accountNumber)
                .sortValue("TXN#" + transactionId)
                .build();

        log.info("Finding transaction {} for account {}, user: {}",
                transactionId, accountNumber, defaultUserId);

        return Optional.ofNullable(transactionTable.getItem(key));
    }

    /**
     * Find all transactions for a specific account
     */
    public List<Transaction> findByAccountNumber(String accountNumber) {
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder()
                        .partitionValue("ACCOUNT#" + accountNumber)
                        .build());

        log.info("Finding all transactions for account {}, user: {}",
                accountNumber, defaultUserId);

        PageIterable<Transaction> pages = transactionTable.query(queryConditional);

        List<Transaction> transactions = new ArrayList<>();
        pages.items().forEach(transactions::add);
        return transactions;
    }

    /**
     * Find all transactions for a specific consent ID
     */
    public List<Transaction> findByConsentId(String consentId) {
        log.info("Finding transactions for consent {}, user: {}",
                consentId, defaultUserId);

        // We need to do a scan with a filter since we don't have GSI in the entity
        List<Transaction> allTransactions = new ArrayList<>();
        PageIterable<Transaction> pages = transactionTable.scan();

        for (Page<Transaction> page : pages) {
            for (Transaction txn : page.items()) {
                if (consentId.equals(txn.getConsentId())) {
                    allTransactions.add(txn);
                }
            }
        }

        log.info("Found {} transactions for consent {}",
                allTransactions.size(), consentId);
        return allTransactions;
    }

    /**
     * Find all transactions for a specific FiAccount ID
     */
    public List<Transaction> findByFiAccountId(Long fiAccountId) {
        log.info("Finding transactions for FiAccount {}, user: {}",
                fiAccountId, defaultUserId);

        // We need to do a scan with a filter since we don't have GSI in the entity
        List<Transaction> allTransactions = new ArrayList<>();
        PageIterable<Transaction> pages = transactionTable.scan();

        for (Page<Transaction> page : pages) {
            for (Transaction txn : page.items()) {
                if (fiAccountId.equals(txn.getFiAccountId())) {
                    allTransactions.add(txn);
                }
            }
        }

        log.info("Found {} transactions for FiAccount {}",
                allTransactions.size(), fiAccountId);
        return allTransactions;
    }

    /**
     * Delete a transaction by account number and transaction ID
     */
    public void deleteById(String accountNumber, String transactionId) {
        Key key = Key.builder()
                .partitionValue("ACCOUNT#" + accountNumber)
                .sortValue("TXN#" + transactionId)
                .build();

        log.info("Deleting transaction {} for account {}, user: {}",
                transactionId, accountNumber, defaultUserId);

        transactionTable.deleteItem(key);
    }

    /**
     * Delete all transactions for an account
     */
    public void deleteByAccountNumber(String accountNumber) {
        log.info("Deleting all transactions for account {}, user: {}",
                accountNumber, defaultUserId);

        List<Transaction> transactions = findByAccountNumber(accountNumber);
        for (Transaction txn : transactions) {
            deleteById(accountNumber, txn.getTxnId());
        }

        log.info("Deleted {} transactions for account {}",
                transactions.size(), accountNumber);
    }

    /**
     * Count all transactions in the table
     */
    public long count() {
        long count = transactionTable.scan().items().stream().count();
        log.info("Counted {} total transactions, user: {}", count, defaultUserId);
        return count;
    }
}