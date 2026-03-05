package com.example.librarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.librarymanagement.entity.LibraryRecordEntity;
import com.example.librarymanagement.service.ServiceLayer;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private ServiceLayer service;

    @PostMapping("/issue/student")
    public ResponseEntity<String> issueStudent(@RequestParam String rollNo,@RequestParam Long accessNo) {
        String result = service.issueBook("STUDENT", rollNo, accessNo);

        if (isError(result)) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/issue/faculty")
    public ResponseEntity<String> issueFaculty(@RequestParam String facultyId,@RequestParam Long accessNo) {
        String result = service.issueBook("FACULTY", facultyId, accessNo);
        if (isError(result)) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam Long accessNo) {
        String result = service.returnBook(accessNo);

        if (isError(result)) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
 
    private boolean isError(String msg) {
        return msg.toLowerCase().contains("exceeded")
            || msg.toLowerCase().contains("not")
            || msg.toLowerCase().contains("failed")
            || msg.toLowerCase().contains("invalid");
    }
    
    //pay fine
    @PostMapping("/pay")
    public String payFine(@RequestParam Long recordId) {
        return service.payFine(recordId);
    }
    
    @GetMapping("/all")
    public List<LibraryRecordEntity> getAllRecords() {
        return service.getAllRecords();
    }
}
