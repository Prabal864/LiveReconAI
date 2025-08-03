package com.micronauticals.accountservice.mapper;


import com.micronauticals.accountservice.Dto.response.financialdata.FIPResponseDTO;
import com.micronauticals.accountservice.entity.financialdata.FiAccount;
import com.micronauticals.accountservice.entity.financialdata.FiDataBundle;
import com.micronauticals.accountservice.entity.financialdata.Fip;
import com.micronauticals.accountservice.entity.financialdata.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FIPResponseDtoToEntityMapper {

    public FiDataBundle mapToEntity(FIPResponseDTO dto) {
        return FiDataBundle.builder()
                .id(dto.getId())
                .status(dto.getStatus())
                .consentId(dto.getConsentId())
                .format(dto.getFormat())
                .dataRange(mapDataRange(dto.getDataRange()))
                .fips(mapFipList(dto.getFips()))
                .build();
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
        List<Transaction> txns = Optional.ofNullable(dto.getData())
                .map(FIPResponseDTO.Fip.Account.AccountData::getAccount)
                .map(FIPResponseDTO.Fip.Account.AccountData.AccountDetail::getTransactions)
                .map(FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Transactions::getTransaction)
                .map(this::mapTransactionList)
                .orElse(null);

        FiAccount entity = FiAccount.builder()
                .maskedAccNumber(dto.getMaskedAccNumber())
                .linkRefNumber(dto.getLinkRefNumber())
                .fistatus(dto.getFIstatus())
                .data(data)
                .transactions(txns)
                .build();

        if (entity.getTransactions() != null) {
            entity.getTransactions().forEach(t -> t.setFiAccount(entity));
        }
        return entity;
    }

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

    private List<Transaction> mapTransactionList(List<FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Transactions.Transaction> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(this::mapTransaction)
                .collect(Collectors.toList());
    }

    private Transaction mapTransaction(FIPResponseDTO.Fip.Account.AccountData.AccountDetail.Transactions.Transaction dto) {
        if (dto == null) return null;
        return Transaction.builder()
                .txnId(dto.getTxnId())
                .amount(parseDouble(dto.getAmount()))
                .currentBalance(parseDouble(dto.getCurrentBalance()))
                .mode(dto.getMode())
                .narration(dto.getNarration())
                .reference(dto.getReference())
                .transactionTimestamp(parseLocalDateTime(dto.getTransactionTimestamp()))
                .type(dto.getType())
                .valueDate(parseLocalDateTime(dto.getValueDate()))
                .build();
    }

    private Double parseDouble(String val) {
        try { return val != null ? Double.valueOf(val) : null; }
        catch (Exception e) { return null; }
    }

    private LocalDateTime parseLocalDateTime(String val) {
        if (val == null) return null;
        try {
            return LocalDateTime.parse(val, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception ignored) { }
        // Add additional parsing as needed
        return null;
    }
}