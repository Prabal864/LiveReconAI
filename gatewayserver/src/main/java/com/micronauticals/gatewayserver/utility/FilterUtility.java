package com.micronauticals.gatewayserver.utility;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {
    public static final String CORRELATION_ID = "livereconai-correlation-id";
    
    public String getCorrelationID (HttpHeaders requestHeaders){
        if(requestHeaders.get(CORRELATION_ID) != null ){
            List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
            return requestHeaderList.stream().findFirst().orElse(null);
        }else {
            return null;
        }
    }
    
    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value){
        return exchange.mutate().request(exchange.getRequest().mutate().header(name,value).build()).build();
    }
    
    public ServerWebExchange setCorrelationID(ServerWebExchange exchange, String correlationID){
        return this.setRequestHeader(exchange,CORRELATION_ID,correlationID);
    }
    
}
