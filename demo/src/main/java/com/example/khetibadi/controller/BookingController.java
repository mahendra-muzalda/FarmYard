 package com.example.khetibadi.controller;

import java.util.List;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.khetibadi.model.Booking;
import com.example.khetibadi.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(
        @RequestParam Long userId,
        @RequestParam Long machineId){
        return bookingService.createBooking(userId,machineId);
    }

    @GetMapping
    public List<Booking> getBookings(){
        return bookingService.getBookings();
    }

    public Booking requestMachine(
            @RequestParam Long userId,
            @RequestParam String machineType){
        return bookingService.createBooking(userId,machineType);
    }

    @PutMapping("/{id}/complete")
    public Booking completBooking(@PathVariable Long id){
        return bookingService.completeBooking(id);
    }

    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable Long id){
        bookingService.cancelBooking(id);
    }

}
