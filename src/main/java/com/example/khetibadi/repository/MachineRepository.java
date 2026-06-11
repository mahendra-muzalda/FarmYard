package com.example.khetibadi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.khetibadi.model.Machine;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    List<Machine> findByMachineType(String machineType);

    List<Machine> findByLocation(String location);

    List<Machine> findByPricePerHourLessThan(Double price);
}
