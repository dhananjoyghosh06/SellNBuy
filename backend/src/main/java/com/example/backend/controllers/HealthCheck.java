package com.example.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin
@RequestMapping("/check")
public class HealthCheck {
    @Autowired
    UserRepo userRepo;

    @GetMapping("/get")
    public List<User> getMethodName() {
        return userRepo.findAll();
    }

    @GetMapping("/testing")
    public ResponseEntity<?> testingHai() {
        System.out.println("Eseche");
        return new ResponseEntity<>("All Set", HttpStatus.OK);
    }

}
