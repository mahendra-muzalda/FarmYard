package com.example.khetibadi.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.khetibadi.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByUsername(String username);
    
}
