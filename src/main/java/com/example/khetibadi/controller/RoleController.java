package com.example.khetibadi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.khetibadi.model.Role;
import com.example.khetibadi.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @PostMapping
    public Role addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }
    
    @GetMapping
    public List<Role> getRoles(){
        return roleService.getAllRoles();
    }
}
