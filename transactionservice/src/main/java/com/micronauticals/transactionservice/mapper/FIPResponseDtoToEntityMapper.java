package com.micronauticals.transactionservice.mapper;


import com.micronauticals.transactionservice.dto.FIPResponseDTO;
import com.micronauticals.transactionservice.entity.financialdata.FiAccount;
import com.micronauticals.transactionservice.entity.financialdata.FiDataBundle;
import com.micronauticals.transactionservice.entity.financialdata.Fip;
import com.micronauticals.transactionservice.entity.financialdata.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Maps FIPResponseDTO to entities with transactions extracted separately
 *
 * Current Date and Time (UTC - YYYY-MM-DD HH:MM:SS formatted): 2025-08-12 12:44:23
 * Current User's Login: Prabal864
 */
@Component
public class FIPResponseDtoToEntityMapper {

    private static final Logger log = LoggerFactory.getLogger(FIPResponseDtoToEntityMapper.class);
    private final DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final TransactionMapper transactionMapper;

    @Value("${app.user.id:Prabal864}")
    private String defaultUserId;

    // Store raw transaction data with account information for mapping
    private final Map<String, TransactionDataEntry> transactionDataMap = new HashMap<>();

    @Autowired
    public FIPResponseDtoToEntityMapper(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
        log.info("Initialized FIPResponseDtoToEntityMapper at {}, user: {}",
                LocalDateTime.now().format(timestampFormatter), defaultUserId);
    }

    public FiDataBundle mapToEntity(FIPResponseDTO dto) {
        // Clear transaction map before mapping
        transactionDataMap.clear();

        log.info("Mapping FIPResponseDTO to entity for consent ID: {}, user: {}",
                dto.getConsentId(), defaultUserId);

        FiDataBundle bundle = FiDataBundle.builder()
                .id(dto.getId())
                .status(dto.getStatus())
                .consentId(dto.getConsentId())
                .format(dto.getFormat())
                .dataRange(mapDataRange(dto.getDataRange()))
                .fips(mapFipList(dto.getFips()))
                .build();

        return bundle;
    }

    /**
     * Extract all transactions as DynamoDB entities
     * This should be called after mapToEntity to get transactions for DynamoDB
     */
    public List<Transaction> extractAllTransactions(String consentId) {
        log.info("Extracting all transactions for consent {}, user: {}",
                consentId, defaultUserId);

        List<Transaction> allTransactions = new ArrayList<>();

        for (TransactionDataEntry entry : transactionDataMap.values()) {
            // Use the dedicated transaction mapper
            List<Transaction> transactions = transactionMapper.mapToDynamoDbTransactions(
                    entry.transactions,
                    entry.accountNumber,
                    consentId,
                    entry.fiAccountId
            );

            allTransactions.addAll(transactions);
        }

        log.info("Extracted {} transactions for DynamoDB, user: {}",
                allTransactions.size(), defaultUserId);
        return allTransactions;
    }

    private FiDataBundle.DataRange mapDataRange(FIPResponseDTO.DataRange dto) {
        if (dto == null) return null;
        return new FiDataBundle.DataRange(dto.getFrom(), dto.getTo());
    }

    private List<Fip> mapFipList(List<FIPResponseDTO.Fip> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(this::mapFip)
                .collect(Collectors.toList());
    }

    private Fip mapFip(FIPResponseDTO.Fip dto) {
        Fip fip = Fip.builder()
                .fipID(dto.getFipID())
                .accounts(mapAccountList(dto.getAccounts()))
                .build();
        if (fip.getAccounts() != null) {
            fip.getAccounts().forEach(a -> a.setFip(fip));
        }
        return fip;
    }

    private List<FiAccount> mapAccountList(List<FIPResponseDTO.Fip.Account> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(this::mapAccount)
                .collect(Collectors.toList());
    }

    private FiAccount mapAccount(FIPResponseDTO.Fip.Account dto) {
        FiAccount.AccountData data = mapAccountData(dto.getData());

        // Extract raw transaction data but don't map it yet
        List<FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Transactions.Transaction> txnData =
                Optional.ofNullable(dto.getData())
                        .map(FIPResponseDTO.Fip.Account.AccountData::getAccount)
                        .map(FIPResponseDTO.Fip.Account.AccountData.AccountDetail::getTransactions)
                        .map(FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Transactions::getTransaction)
                        .orElse(null);

        // Build account WITHOUT transactions
        FiAccount entity = FiAccount.builder()
                .maskedAccNumber(dto.getMaskedAccNumber())
                .linkRefNumber(dto.getLinkRefNumber())
                .fistatus(dto.getFIstatus())
                .data(data)
                // No transactions here - they'll be handled separately
                .build();

        // Store the entity ID after it's created
        Long accountId = entity.getId();

        // Store raw transaction data with account information for later processing
        if (txnData != null && !txnData.isEmpty()) {
            TransactionDataEntry entry = new TransactionDataEntry();
            entry.transactions = txnData;
            entry.accountNumber = dto.getLinkRefNumber();
            entry.fiAccountId = accountId;

            transactionDataMap.put(dto.getLinkRefNumber(), entry);
            log.debug("Stored {} raw transactions for account {}, user: {}",
                    txnData.size(), dto.getLinkRefNumber(), defaultUserId);
        }

        return entity;
    }

    // Helper class to store transaction data with account information
    private static class TransactionDataEntry {
        List<FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Transactions.Transaction> transactions;
        String accountNumber;
        Long fiAccountId;
    }

    // Remaining methods unchanged...
    private FiAccount.AccountData mapAccountData(FIPResponseDTO.Fip.Account.AccountData dto) {
        if (dto == null) return null;
        return new FiAccount.AccountData(mapAccountDetail(dto.getAccount()));
    }

    private FiAccount.AccountDetail mapAccountDetail(FIPResponseDTO.Fip.Account.AccountData.AccountDetail dto) {
        if (dto == null) return null;
        return new FiAccount.AccountDetail(
                dto.getLinkedAccRef(),
                dto.getMaskedAccNumber(),
                dto.getType(),
                dto.getVersion(),
                mapProfile(dto.getProfile()),
                mapSummary(dto.getSummary())
        );
    }

    private FiAccount.Profile mapProfile(FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Profile dto) {
        if (dto == null) return null;
        return new FiAccount.Profile(mapHolders(dto.getHolders()));
    }

    private FiAccount.Holders mapHolders(FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Profile.Holders dto) {
        if (dto == null) return null;
        return new FiAccount.Holders(
                dto.getType(),
                Optional.ofNullable(dto.getHolder())
                        .map(list -> list.stream().map(this::mapHolder).collect(Collectors.toList()))
                        .orElse(null)
        );
    }

    private FiAccount.Holder mapHolder(FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Profile.Holders.Holder dto) {
        if (dto == null) return null;
        return new FiAccount.Holder(
                dto.getAddress(),
                dto.getCkycCompliance(),
                dto.getDob(),
                dto.getEmail(),
                dto.getMobile(),
                dto.getName(),
                dto.getNominee(),
                dto.getPan()
        );
    }

    private FiAccount.Summary mapSummary(FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Summary dto) {
        if (dto == null) return null;
        return new FiAccount.Summary(
                dto.getBalanceDateTime(),
                dto.getBranch(),
                dto.getCurrency(),
                dto.getCurrentBalance(),
                dto.getCurrentODLimit(),
                dto.getDrawingLimit(),
                dto.getFacility(),
                dto.getIfscCode(),
                dto.getMicrCode(),
                dto.getOpeningDate(),
                mapPending(dto.getPending()),
                dto.getStatus(),
                dto.getType()
        );
    }

    private FiAccount.Pending mapPending(FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Summary.Pending dto) {
        if (dto == null) return null;
        return new FiAccount.Pending(dto.getAmount(), dto.getTransactionType());
    }
}