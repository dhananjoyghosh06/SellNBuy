package com.example.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
        User findByEmail(String email);
}
