package com.example.BasicCrudOperations.Config;

import jakarta.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfig {
    public static final String Message_queue = "crud-queue";
    public static final String topic="Example";

    @Bean
    public JmsTemplate JmsQueueTemplate(ConnectionFactory connectionFactory){
        JmsTemplate template=new JmsTemplate(connectionFactory);
        template.setPubSubDomain(false); // If this is false message model will be Point - to -point Using Queue
        return template;
    }



    @Bean
    public DefaultJmsListenerContainerFactory JmsQueueTemplateFactory(ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory=new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        defaultJmsListenerContainerFactory.setPubSubDomain(false);
        return defaultJmsListenerContainerFactory;
    }


}
