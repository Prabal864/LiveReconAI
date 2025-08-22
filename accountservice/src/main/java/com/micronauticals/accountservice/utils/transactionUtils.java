package com.micronauticals.accountservice.utils;

import com.micronauticals.accountservice.config.RabbitMQConfig;
import com.micronauticals.accountservice.entity.financialdata.FiDataBundle;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class transactionUtils {

    private AmqpTemplate amqpTemplate;

    public void saveTransaction(FiDataBundle data) {
        amqpTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                data
        );
    }
}
