package com.example.khetibadi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.khetibadi.model.Conversation;
import com.example.khetibadi.repository.ConversationRepository;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public Conversation create(Conversation conversation){
        return conversationRepository.save(conversation);
    }

    public List<Conversation> getAll(){
        return conversationRepository.findAll();
    }
}
