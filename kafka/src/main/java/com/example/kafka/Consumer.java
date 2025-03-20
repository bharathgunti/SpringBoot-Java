package com.example.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(id = "myConsumer", topics = "First_topic", groupId = "springboot-group-new", autoStartup = "true")
    public void listen(String value,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        logger.info("\n\n✅ Consumer is running and listening!\n\n");
        logger.info("\n\n✅ Consumed event from topic {}: key = {} | value = {}\n\n", topic, key, value);
    }
}
