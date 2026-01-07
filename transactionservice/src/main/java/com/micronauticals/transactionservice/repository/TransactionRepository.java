package com.micronauticals.transactionservice.repository;

import com.micronauticals.transactionservice.entity.financialdata.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TransactionRepository {

    private static final Logger log = LoggerFactory.getLogger(TransactionRepository.class);
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbTable<Transaction> transactionTable;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String tableName;

    @Value("${app.user.id:Prabal864}")
    private String defaultUserId;

    @Value("${aws.dynamodb.batch.concurrency:10}")
    private int concurrencyLevel;

    @Autowired
    public TransactionRepository(
            DynamoDbEnhancedClient dynamoDbEnhancedClient,
            @Value("${aws.dynamodb.table.name:Transaction_Account_Service}") String tableName) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.tableName = tableName;
        this.transactionTable = dynamoDbEnhancedClient.table(tableName,
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
     * Save multiple transactions to DynamoDB using parallel batch processing with controlled concurrency
     * Uses a thread pool to process batches in parallel with configurable concurrency
     */
    public List<Transaction> saveAll(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            log.info("No transactions to save, user: {}", defaultUserId);
            return transactions;
        }

        final int batchSize = 25; // DynamoDB BatchWriteItem limit
        final int totalTransactions = transactions.size();
        final int totalBatches = (int) Math.ceil((double) totalTransactions / batchSize);

        log.info("Starting parallel batch save of {} transactions using {} threads, user: {}",
                totalTransactions, concurrencyLevel, defaultUserId);

        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "DynamoDbBatchWriter-" + threadNumber.getAndIncrement());
                thread.setDaemon(true);
                return thread;
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(concurrencyLevel, threadFactory);

        List<List<Transaction>> batches = new ArrayList<>();
        for (int i = 0; i < transactions.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, transactions.size());
            batches.add(new ArrayList<>(transactions.subList(i, endIndex)));
        }

        final AtomicInteger successfulBatches = new AtomicInteger(0);
        final AtomicInteger failedBatches = new AtomicInteger(0);
        final CountDownLatch latch = new CountDownLatch(batches.size());

        List<Future<?>> futures = new ArrayList<>();
        for (int batchNum = 0; batchNum < batches.size(); batchNum++) {
            final int currentBatchNum = batchNum;
            final List<Transaction> currentBatch = batches.get(batchNum);

            futures.add(executor.submit(() -> {
                try {
                    processBatch(currentBatch, currentBatchNum + 1, totalBatches);
                    successfulBatches.incrementAndGet();
                } catch (Exception e) {
                    failedBatches.incrementAndGet();
                    log.error("Error processing batch {}/{}, user: {}, error: {}",
                            currentBatchNum + 1, totalBatches, defaultUserId, e.getMessage(), e);
                } finally {
                    latch.countDown();
                }
            }));
        }

        try {
            boolean completed = latch.await(5, TimeUnit.MINUTES);
            if (!completed) {
                log.warn("Timeout waiting for batch operations to complete after 5 minutes, user: {}",
                        defaultUserId);
            }
        } catch (InterruptedException e) {
            log.error("Thread interrupted while waiting for batch operations, user: {}",
                    defaultUserId, e);
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        log.info("Completed parallel batch processing: {}/{} batches successful, {}/{} batches failed, user: {}",
                successfulBatches.get(), totalBatches, failedBatches.get(), totalBatches, defaultUserId);

        return transactions;
    }

    /**
     * Process a single batch of transactions
     */
    private void processBatch(List<Transaction> batch, int batchNum, int totalBatches) {
        String timestamp = LocalDateTime.now().format(formatter);
        log.info("Processing batch {}/{} with {} items at {}, user: {}",
                batchNum, totalBatches, batch.size(), timestamp, defaultUserId);

        WriteBatch.Builder<Transaction> writeBuilder = WriteBatch.builder(Transaction.class)
                .mappedTableResource(transactionTable);

        for (Transaction txn : batch) {
            writeBuilder.addPutItem(txn);
        }

        BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder()
                .writeBatches(writeBuilder.build())
                .build();

        dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest);

        log.info("Successfully completed batch {}/{} with {} items, user: {}",
                batchNum, totalBatches, batch.size(), defaultUserId);
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
        log.info("Finding all transactions for account {} using GSI 3, user: {}", accountNumber, defaultUserId);
        DynamoDbIndex<Transaction> gsi3Index = transactionTable.index("pk_GSI_3-sk_GSI_3-index");
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder()
                        .partitionValue("ACCOUNT#" + accountNumber)
                        .build());

        SdkIterable<Page<Transaction>> pages = gsi3Index.query(QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .build());

        List<Transaction> transactions = new ArrayList<>();
        pages.forEach(page -> transactions.addAll(page.items()));
        log.info("Found {} transactions for account {}", transactions.size(), accountNumber);
        return transactions;
    }

    /**
     * Find all transactions for a specific consent ID using partition key query
     * This is much faster and more cost-effective than scanning the entire table
     */
    public List<Transaction> findByConsentId(String consentId) {
        log.info("Finding transactions for consent {} using partition key query, user: {}",
                consentId, defaultUserId);

        // Build the partition key value
        String partitionKey = "CONSENTID#" + consentId;

        // Create query conditional for partition key
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder()
                        .partitionValue(partitionKey)
                        .build());

        // Execute query
        SdkIterable<Page<Transaction>> pages = transactionTable.query(QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .build());

        // Collect all results
        List<Transaction> allTransactions = new ArrayList<>();
        pages.forEach(page -> allTransactions.addAll(page.items()));

        log.info("Found {} transactions for consent {} using efficient query",
                allTransactions.size(), consentId);
        return allTransactions;
    }

    /**
     * Find all transactions for a specific FiAccount ID
     */
    public List<Transaction> findByFiAccountId(Long fiAccountId) {
        log.info("Finding transactions for FiAccount {}, user: {}",
                fiAccountId, defaultUserId);

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
