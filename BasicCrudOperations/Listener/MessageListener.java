package com.example.BasicCrudOperations.Listener;

import com.example.BasicCrudOperations.Config.JmsConfig;
import com.example.BasicCrudOperations.Model.MessageEntity;
import com.example.BasicCrudOperations.Repository.MessageRepository;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageListener {

    @Autowired
    private MessageRepository messageRepository;


    @JmsListener(destination = JmsConfig.Message_queue,containerFactory = "JmsQueueTemplateFactory")
    public void SaveMessage(Message message){

        try {
            if (message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                MessageEntity messageEntity = new MessageEntity();
                messageEntity.setContent(text);
                messageEntity.setReceivedAt(LocalDateTime.now());
                messageRepository.save(messageEntity);
                System.out.println("Received and stored message: " + text);
            }
        } catch (JMSException e) {
            System.err.println("Error"+" "+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
