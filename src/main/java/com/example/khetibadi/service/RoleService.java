package com.example.khetibadi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.khetibadi.model.Role;
import com.example.khetibadi.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role addRole(Role role){
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
}
