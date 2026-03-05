package com.example.librarymanagement.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.librarymanagement.entity.*;
import com.example.librarymanagement.repository.*;
import com.example.librarymanagement.service.ServiceLayer;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/books")
public class BookController {

    @Autowired
    private ServiceLayer service;

    @Autowired
    private BookRepository bookRepo;

    // Add books 
    @PostMapping("/add")
    public String addBooks(@RequestParam String bookName, @RequestParam int count) {
        service.addBookWithCount(bookName, count);
        return "Books add request processed";
    }

    // get all books
    @GetMapping("/all")
    public List<BookEntity> getAllBooks() {
        return bookRepo.findAll();
    }

    // get available books
    @GetMapping("/available")
    public List<BookEntity> getAvailableBooks() {
        return bookRepo.findAll()
                .stream()
                .filter(b -> b.getStatus().equals("AVAILABLE"))
                .toList();
    }
}
