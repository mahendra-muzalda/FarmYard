package com.example.khetibadi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.khetibadi.model.Machine;
import com.example.khetibadi.model.User;
import com.example.khetibadi.repository.MachineRepository;

import util.DistanceCalculator;

@Service
public class DispatchService {

    private final MachineRepository machineRepository;

    public DispatchService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public Machine findNearestMachine(User user, String machineType){
        List<Machine> machines = machineRepository.findByMachineType(machineType);

        Machine nearestMachine = null;
        double minDistance = Double.MAX_VALUE;

        for(Machine machine : machines){
            double distance = DistanceCalculator.calculate(
                user.getLatitude(),
                user.getLongitude(),
                machine.getLatitude(),
                machine.getLongitude()
            );

            if(distance < minDistance){
                minDistance = distance;
                nearestMachine = machine;
            }
        }

        return nearestMachine;
    }
}
