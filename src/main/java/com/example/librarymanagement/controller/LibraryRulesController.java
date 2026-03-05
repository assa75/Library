package com.example.librarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.librarymanagement.entity.LibraryRules;
import com.example.librarymanagement.repository.LibraryRulesRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rules")
public class LibraryRulesController {

    @Autowired
    private LibraryRulesRepository rulesRepo;

    @PostMapping("/add")
    public String addRules(@RequestBody LibraryRules rules) {
        try {
            rulesRepo.save(rules);
            System.out.println("Rules saved successfully");
            return "Rules saved successfully";
        } catch (Exception e) {
            System.out.println("Error saving rules");
            e.printStackTrace();
            return "Failed to save rules";
        }
    }

}

