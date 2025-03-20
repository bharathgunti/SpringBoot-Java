package com.example.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "First_topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String key, String value) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC, key, value);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("✅ Message sent successfully! Topic: {} | Key: {} | Value: {}",
                        result.getRecordMetadata().topic(), key, value);
            } else {
                logger.error("❌ Failed to send message. Key: {} | Value: {}", key, value, ex);
            }
        });
    }
}