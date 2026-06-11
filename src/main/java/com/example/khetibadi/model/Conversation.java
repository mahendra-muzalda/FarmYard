package com.example.khetibadi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Conversation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Machine machine;

    @ManyToOne
    private User farmer;

    @ManyToOne
    private User owner;

    public Long getId() {
        return id;
    }

    public Machine getMachine() {
        return machine;
    }

    public User getFarmer() {
        return farmer;
    }

    public User getOwner() {
        return owner;
    }


}
