package com.example.librarymanagement.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.librarymanagement.entity.*;
import com.example.librarymanagement.repository.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    // Insert single user
    @PostMapping("/add")
    public String addUser(@RequestBody UserEntity user) {
        try {
            userRepo.save(user);
            System.out.println("User inserted successfully");
            return "User added";
        } catch(Exception e) {
            System.out.println("User insert error");
            return "Error inserting user";
        }
    }

    // Insert multiple users
    @PostMapping("/multiple")
    public String addAllUsers(@RequestBody List<UserEntity> users) {
        try {
            userRepo.saveAll(users);
            System.out.println("Users inserted successfully");
            return "Users added";
        } catch(Exception e) {
            System.out.println("Multiple insert error");
            return "Error inserting users";
        }
    }

    // Get all users
    @GetMapping("/all")
    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    // Get student by rollNo
    @GetMapping("/student/{rollNo}")
    public UserEntity getStudent(@PathVariable String rollNo) {
        return userRepo.findByRollNo(rollNo);
    }

    // Get faculty by facultyId
    @GetMapping("/faculty/{facultyId}")
    public UserEntity getFaculty(@PathVariable String facultyId) {
        return userRepo.findByFacultyId(facultyId);
    }
} 
