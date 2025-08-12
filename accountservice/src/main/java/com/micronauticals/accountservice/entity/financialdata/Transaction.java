package com.micronauticals.accountservice.entity.financialdata;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private String pk;
    private String sk;
    private String txnId;
    private String accountNumber;
    private String consentId;
    private Double amount;
    private Double currentBalance;
    private String mode;
    private String narration;
    private String reference;
    private String transactionTimestamp;
    private String type;
    private String valueDate;
    private String userId;

    private Long fiAccountId;
    private String maskedAccNumber;
    private String accountType;
    private String accountStatus;
    private String linkRefNumber;

    private String createdAt;
    private String updatedAt;

    @DynamoDbPartitionKey
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    public String getSk() {
        return "ACCOUNT#" + accountNumber + "#TXN#" + txnId;
    }


}