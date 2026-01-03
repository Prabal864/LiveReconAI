package com.micronauticals.transactionservice.entity.financialdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    // ...existing code...
    private String pk; // consentID
    private String sk; // Account Number + Txn TimeStamp

    private String pk_GSI_1; // Type
    private String sk_GSI_1; // Amount

    private String pk_GSI_2; // Date
    private String sk_GSI_2; // Mode + Amount

    private String pk_GSI_3; // Account Number
    private String sk_GSI_3; // Narration + Amount

    private String pk_GSI_4; // Narration
    private String sk_GSI_4; // Amount + Mode

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

    @DynamoDbSecondaryPartitionKey(indexNames = "pk_GSI_1-sk_GSI_1-index")
    public String getPk_GSI_1() {
        return "TYPE#" + type;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "pk_GSI_3-sk_GSI_3-index")
    public String getPk_GSI_3() {
        return "ACCOUNT#" + accountNumber;
    }

    @DynamoDbSecondarySortKey(indexNames = "pk_GSI_3-sk_GSI_3-index")
    public String getSk_GSI_3(String ignored) {
        return "NARRATION#" + narration + "#AMOUNT#" + String.format("%.2f", amount);
    }

    public void setPk_GSI_1(String pk_GSI_1) {
        this.pk_GSI_1 = pk_GSI_1;
    }

    @DynamoDbSecondarySortKey(indexNames = "pk_GSI_1-sk_GSI_1-index")
    public String getSk_GSI_1() {
        return "AMT#" + String.format("%020.2f", amount);
    }

    public void setSk_GSI_1(String sk_GSI_1) {
        this.sk_GSI_1 = sk_GSI_1;
    }

    @DynamoDbPartitionKey
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    public String getSk() {
        return "ACCOUNT#" + accountNumber + "#TIMESTP#" + transactionTimestamp;
    }

    // ...existing code...
}

