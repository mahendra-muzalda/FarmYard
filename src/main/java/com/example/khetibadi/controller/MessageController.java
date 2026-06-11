package com.example.khetibadi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.khetibadi.model.Message;
import com.example.khetibadi.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public Message send(@RequestBody Message message){
        return messageService.send(message);
    }

    @GetMapping
    public List<Message> getAll(){
        return messageService.getAll();
    }
}
