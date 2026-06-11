package com.example.khetibadi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.khetibadi.model.Booking;
import com.example.khetibadi.model.Machine;
import com.example.khetibadi.repository.BookingRepository;
import com.example.khetibadi.repository.MachineRepository;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final BookingRepository bookingRepository;

    public MachineService(MachineRepository machineRepository, BookingRepository bookingRepository) {
        this.machineRepository = machineRepository;
        this.bookingRepository = bookingRepository;
    }

    public Machine addMachine(Machine machine){
        machine.setStatus("Available");
        return machineRepository.save(machine);
    }

    public List<Machine> getAllMachines(){
        return machineRepository.findAll();
    }

    public List<Machine> searchByType(String type){
        return machineRepository.findByMachineType(type);
    }

    public List<Machine> searchByLocation(String location){
        return machineRepository.findByLocation(location);
    }

    public List<Machine> searchByPrice(Double price){
        return machineRepository.findByPricePerHourLessThan(price);
    }


    //check availability
    public String checkAvailability(Long machineId){
        Machine machine = machineRepository.findById(machineId).orElseThrow();
        List<Booking> bookings = bookingRepository.findByMachine(machine);

        if (bookings.isEmpty()) {
            return "Available";
        }

        return "BOOKED";
    }
}
