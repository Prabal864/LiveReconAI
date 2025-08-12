package com.micronauticals.accountservice.service.SetuServiceImpl;

import com.micronauticals.accountservice.config.RabbitMQConfig;
import com.micronauticals.accountservice.entity.financialdata.FiDataBundle;
import com.micronauticals.accountservice.repository.FIDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class fiDataBundleConsumer {
    private final List<FiDataBundle> buffer = new ArrayList<>();
    private static final int BATCH_SIZE = 500;
    private static final long MAX_WAIT_MS = 5000;
    private long lastFlushTime = System.currentTimeMillis();

    private final FIDataRepository fiDataRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public synchronized void consume(FiDataBundle fiDataBundle) {
        buffer.add(fiDataBundle);
        long now = System.currentTimeMillis();

        if (buffer.size() >= BATCH_SIZE || now - lastFlushTime >= MAX_WAIT_MS) {
            List<FiDataBundle> batchToSave = new ArrayList<>(buffer);
            buffer.clear();
            lastFlushTime = now;
            fiDataRepository.saveAll(batchToSave);
        }
    }
}

