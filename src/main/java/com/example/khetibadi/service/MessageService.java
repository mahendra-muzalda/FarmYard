package com.example.khetibadi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.khetibadi.model.Message;
import com.example.khetibadi.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message send(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAll(){
        return messageRepository.findAll();
    }
}
