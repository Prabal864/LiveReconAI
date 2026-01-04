package com.micronauticals.transactionservice.mapper;


import com.micronauticals.transactionservice.dto.consent.ConsentDataSessionResponseDTO;
import com.micronauticals.transactionservice.entity.consent.ConsentDataSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsentDataSessionToEntity {

    public ConsentDataSession mapToEntity(ConsentDataSessionResponseDTO dto) {
        if (dto == null) return null;

        // Step 1: Build parent entity
        ConsentDataSession consentDataSession = ConsentDataSession.builder()
                .consentId(dto.getConsentId())
                .traceId(dto.getTraceId())
                .build();

        // Step 2: Map DataSession list and set back-reference to parent
        List<ConsentDataSession.DataSession> dataSessionEntities = dto.getDataSessions() != null
                ? dto.getDataSessions().stream()
                .map(ds -> ConsentDataSession.DataSession.builder()
                        .sessionId(ds.getSessionId())
                        .status(ds.getStatus())
                        .created_at(ds.getCreated_at())
                        .consentDataSession(consentDataSession)  // 🔥 important: set parent here
                        .build())
                .collect(Collectors.toList())
                : List.of();

        // Step 3: Set child list in parent
        consentDataSession.setDataSessions(dataSessionEntities);

        return consentDataSession;
    }
}
