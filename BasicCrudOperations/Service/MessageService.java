package com.example.BasicCrudOperations.Service;

import com.example.BasicCrudOperations.Config.JmsConfig;
import com.example.BasicCrudOperations.Model.MessageEntity;
import com.example.BasicCrudOperations.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public ResponseEntity<List<MessageEntity>> getAllMessages() {
            List<MessageEntity> messageEntityList=messageRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(messageEntityList);
    }

}
