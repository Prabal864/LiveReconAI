package com.micronauticals.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator liveReconAIConfig(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(p -> p.path("/prod/v1/account/**")
                        .filters(f->f.rewritePath(
                                "prod/v1/account/(?<segment>.*)",
                                "/${segment}"
                        )).uri("lb://ACCOUNT")
                )
                .route(p -> p.path("/prod/v1/auth/**")
                        .filters(f->f.rewritePath(
                                "prod/v1/auth/(?<segment>.*)",
                                "/${segment}"
                        )).uri("lb://AUTH")
                )
                .build();
    }
}
