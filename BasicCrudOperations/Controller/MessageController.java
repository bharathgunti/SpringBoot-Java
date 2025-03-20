package com.example.BasicCrudOperations.Controller;

import com.example.BasicCrudOperations.Config.JmsConfig;
import com.example.BasicCrudOperations.Model.MessageEntity;
import com.example.BasicCrudOperations.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JmsTemplate jmsTemplate;


@PostMapping("/send")
public ResponseEntity<String> sendtMessage(@RequestBody String message) {
    if (message == null || message.trim().isEmpty()) {
        return ResponseEntity.badRequest().body("Message payload is empty or null.");
    }

    try {
        jmsTemplate.convertAndSend(JmsConfig.Message_queue, message);
        return ResponseEntity.ok("Message sent successfully.");
    } catch (MessageConversionException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Message conversion failed: " + e.getMessage());
    }
}


    @GetMapping("/all")
    public ResponseEntity<List<MessageEntity>> GetAllMessages(){
        return messageService.getAllMessages();
    }
}
