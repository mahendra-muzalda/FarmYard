package com.example.khetibadi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.khetibadi.model.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

}
