package com.example.kafka;




import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    private final Producer producerService;

    public KafkaController(Producer producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/publish")
    public String publishMessage(@RequestParam String key, @RequestParam String message) {
        producerService.sendMessage(key, message);
        return "Message sent successfully!";
    }
}
