package com.example.khetibadi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.khetibadi.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
