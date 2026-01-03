package com.micronauticals.gatewayserver.filters;

import com.micronauticals.gatewayserver.utility.FilterUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestTraceFilter implements GlobalFilter {

    private final FilterUtility filterUtility;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if(isCorrelationIDPresent(requestHeaders)){
            log.debug("livereconai-correlation-id found in RequestTraceFilter : {}",filterUtility.getCorrelationID(requestHeaders));
        }else{
            String correlationId = generateCorrelationId();
            exchange = filterUtility.setCorrelationID(exchange,correlationId);
            log.debug("livereconai-correlation-id generated in RequestTraceFilter : {}", correlationId);
        }
        return chain.filter(exchange);
    }

    private boolean isCorrelationIDPresent(HttpHeaders requestHttpHeaders){
        return filterUtility.getCorrelationID(requestHttpHeaders) != null;
    }

    private String generateCorrelationId(){
        return java.util.UUID.randomUUID().toString();
    }

}
