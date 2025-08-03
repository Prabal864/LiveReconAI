package com.micronauticals.accountservice.mapper;

import com.micronauticals.accountservice.Dto.response.consent.ConsentResponse;
import com.micronauticals.accountservice.entity.consent.Consent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsentDtoToEntity {

    public Consent mapToEntity(ConsentResponse response) {
        ConsentResponse.Detail detailDto = response.getDetail();

        Consent.Detail detailEntity = Consent.Detail.builder()
                .fiTypes(detailDto.getFiTypes())
                .consentStart(detailDto.getConsentStart())
                .consentExpiry(detailDto.getConsentExpiry())
                .fetchType(detailDto.getFetchType())
                .vua(detailDto.getVua())
                .consentMode(detailDto.getConsentMode())
                .consentTypes(detailDto.getConsentTypes())
                .purpose(
                        Consent.Purpose.builder()
                                .text(detailDto.getPurpose().getText())
                                .code(detailDto.getPurpose().getCode())
                                .refUri(detailDto.getPurpose().getRefUri())
                                .category(
                                        Consent.Category.builder()
                                                .type(detailDto.getPurpose().getCategory().getType())
                                                .build()
                                )
                                .build()
                )
                .dataLife(
                        Consent.DataLife.builder()
                                .unit(detailDto.getDataLife().getUnit())
                                .value(detailDto.getDataLife().getValue())
                                .build()
                )
                .frequency(
                        Consent.Frequency.builder()
                                .unit(detailDto.getFrequency().getUnit())
                                .value(detailDto.getFrequency().getValue())
                                .build()
                )
                .dataRange(
                        Consent.DataRange.builder()
                                .from(detailDto.getDataRange().getFrom())
                                .to(detailDto.getDataRange().getTo())
                                .build()
                )
                .build();

        return Consent.builder()
                .id(response.getId())
                .url(response.getUrl())
                .status(response.getStatus())
                .pan(response.getPan())
                .detail(detailEntity)
                .redirectUrl(response.getRedirectUrl())
                .traceId(response.getTraceId())
                .enableAdditionalPhoneNumber(response.getEnableAdditionalPhoneNumber())
                .accountsLinked(mapAccountsLinked(response.getAccountsLinked()))
                .context(mapContext(response.getContext()))
                .tags(response.getTags())
                .usage(mapUsage(response.getUsage()))
                .build();
    }

    private List<Consent.AccountLinked> mapAccountsLinked(List<ConsentResponse.AccountLinked> accountDto){
        if(accountDto == null) return null;
        return accountDto.stream()
                .map(dto -> Consent.AccountLinked.builder()
                        .accountId(dto.getAccountId())
                        .maskedAccountNumber(dto.getMaskedAccountNumber())
                        .bankName(dto.getBankName())
                        .build()
                ).collect(Collectors.toList());
    }

    private List<Consent.Context> mapContext(List<ConsentResponse.Context> contextDto){
        if(contextDto == null) return null;
        return contextDto.stream()
                .map( context -> Consent.Context.builder()
                        .key(context.getKey())
                        .value(context.getValue())
                        .build()
                ).collect(Collectors.toList());
    }

    private Consent.Usage mapUsage(ConsentResponse.Usage usageDto) {
        if (usageDto == null) return null;
        return Consent.Usage.builder()
                .count(usageDto.getCount())
                .lastUsed(usageDto.getLastUsed())
                .build();
    }
}
