package com.micronauticals.accountservice.entity.financialdata;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@SuppressWarnings({"LombokGetterMayBeUsed", "LombokSetterMayBeUsed"})
@DynamoDbBean
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private String pk;
    private String sk;
    private String pk_GSI_1;
    private String sk_GSI_1;
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

    @DynamoDbSecondaryPartitionKey(indexNames = "pk_GSI_1-sk_GSI_1-index" )
    public String getPk_GSI_1() {
        return "TYPE#"+type;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}