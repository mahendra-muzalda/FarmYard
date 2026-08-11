package com.example.khetibadi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.khetibadi.model.Booking;
import com.example.khetibadi.model.BookingStatus;
import com.example.khetibadi.model.Machine;
import com.example.khetibadi.model.User;


public interface BookingRepository extends JpaRepository<Booking,Long> {

    // List<Booking> findByMachineAndStatus(Machine machine, String status);

    List<Booking> findByMachineAndStatus(Machine machine, BookingStatus pending);

    List<Booking> findByMachine(Machine machine);

    List<Booking> findByUser(User user);

}
