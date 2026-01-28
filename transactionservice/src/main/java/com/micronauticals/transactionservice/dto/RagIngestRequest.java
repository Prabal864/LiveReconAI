package com.micronauticals.transactionservice.dto;

import com.micronauticals.transactionservice.entity.financialdata.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RagIngestRequest {
    private List<Transaction> context_data;
}
