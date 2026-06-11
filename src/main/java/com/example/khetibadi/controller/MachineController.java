package com.example.khetibadi.controller;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.khetibadi.model.Machine;
import com.example.khetibadi.service.MachineService;

@RestController
@RequestMapping("/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService){
        this.machineService = machineService;
    }

    @PostMapping
    public Machine addMachine(@RequestBody Machine machine){
        return machineService.addMachine(machine);
    }

    @GetMapping
    public List<Machine> getMachines(){
        return machineService.getAllMachines();
    }

    @GetMapping("/type/{type}")
    public List<Machine> searchType(@PathVariable String type){
        return machineService.searchByType(type);
    }

    @GetMapping("/location/{location}")
    public List<Machine> searchLocation(@PathVariable String location){
        return machineService.searchByLocation(location);
    }

    @GetMapping("/{id}/availability")
    public String checkAvailability(@PathVariable Long id){
        return machineService.checkAvailability(id);
    }

}
