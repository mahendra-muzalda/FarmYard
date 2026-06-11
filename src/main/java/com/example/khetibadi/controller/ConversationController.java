package com.example.khetibadi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.khetibadi.model.Conversation;
import com.example.khetibadi.service.ConversationService;

@RestController
@RequestMapping("/conversations")
public class ConversationController {
    
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public Conversation create(@RequestBody Conversation conversation){
        return conversationService.create(conversation);
    }

    @GetMapping
    public List<Conversation> getAll(){
        return conversationService.getAll();
    }

}
