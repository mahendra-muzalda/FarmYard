package com.example.khetibadi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EnumType;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Machine machine;

    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private Integer queuePosition;

    public Booking(){}

    public Long getId(){ return id; }

    public User getUser(){ return user; }

    public void setUser(User user){ this.user = user; }

    public Machine getMachine(){ return machine; }

    public void setMachine(Machine machine){ this.machine = machine; }

    public LocalDateTime getBookingTime(){ return bookingTime; }

    public void setBookingTime(LocalDateTime bookingTime){
        this.bookingTime = bookingTime;
    }

    public BookingStatus getStatus(){ return status; }

    public void setStatus(BookingStatus status){
        this.status = status;
    }

    public Integer getQueuePosition(){ return queuePosition; }

    public void setQueuePosition(Integer queuePosition){
        this.queuePosition = queuePosition;
    }
}