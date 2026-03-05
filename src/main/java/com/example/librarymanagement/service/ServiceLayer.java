package com.example.librarymanagement.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.librarymanagement.entity.*;
import com.example.librarymanagement.repository.*;
import java.util.stream.IntStream;


@Service
public class ServiceLayer {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private LibraryRecordRepository recordRepo;

    @Autowired
    private LibraryRulesRepository rulesRepo;

    //Add books
    public void addBookWithCount(String bookName, int count) {

        try {
            System.out.println("Add book started");

            List<BookEntity> books = new ArrayList<>();
            
            IntStream.range(0, count).forEach(i -> {
                        BookEntity book = new BookEntity();
                        book.setBookName(bookName);
                        book.setStatus("AVAILABLE");
                        books.add(book);
                    });

            bookRepo.saveAll(books);

            System.out.println(count + " copies of "
                + bookName + " added successfully");

        } catch (Exception e) {
            System.out.println("Error while adding books");
            e.printStackTrace();
        }
    }

    //Issue Book
    public String issueBook(String role, String memberId, Long accessNo) {

        try {
            System.out.println("Issue book started");

            UserEntity user;

            if ("STUDENT".equalsIgnoreCase(role)) {
                user = userRepo.findByRollNo(memberId);
            } else if ("FACULTY".equalsIgnoreCase(role)) {
                user = userRepo.findByFacultyId(memberId);
            } else {
                return "Invalid role";
            }

            if (user == null || !user.getRole().equalsIgnoreCase(role)) {
                return "Invalid user";
            }

            LibraryRules rules = rulesRepo.findById(role).orElse(null);
            if (rules == null) {
                return "Rules not found";
            }

            long activeBooks =
                recordRepo.countByMemberIdAndReturnDateIsNull(memberId);

            if (activeBooks >= rules.getMaxLimits()) {
                return role + " book limit exceeded. Return a book first.";
            }

            BookEntity book = bookRepo.findById(accessNo).orElse(null);
            if (book == null || "ISSUED".equalsIgnoreCase(book.getStatus())) {
                return "Book not available";
            }

            LibraryRecordEntity record = new LibraryRecordEntity();
            record.setMemberId(memberId);
            record.setRole(role);
            record.setAccessNo(accessNo);
            record.setIssueDate(LocalDate.now());
            record.setDueDate(LocalDate.now().plusDays(rules.getDueDays()));
            record.setFine(0);
            record.setFineStatus("NONE");
            record.setReturnDate(null); 

            recordRepo.save(record);

            book.setStatus("ISSUED");
            bookRepo.save(book);

            long remaining = rules.getMaxLimits() - (activeBooks + 1);
            System.out.println(
                role + " remaining book limit: " + remaining
            );

            return "Book issued successfully";

        } catch (Exception e) {
            e.printStackTrace();
            return "Issue failed";
        }
    }

    // Return Book
    public String returnBook(Long accessNo) {

        try {
            System.out.println("Return process started");

            LibraryRecordEntity record =
                recordRepo.findByAccessNoAndReturnDateIsNull(accessNo);

            if (record == null) {
                return "Record not found or already returned";
            }

            LocalDate today = LocalDate.now();
            record.setReturnDate(today);

            LibraryRules rules = rulesRepo.findById(record.getRole()).orElse(null);

            if ("STUDENT".equalsIgnoreCase(record.getRole()) && rules != null) {

                long daysLate = ChronoUnit.DAYS.between(record.getDueDate(), today);

                if (daysLate > 0) {
                    record.setFine((int) (daysLate * rules.getFine()));
                    record.setFineStatus("UNPAID");
                } else {
                    record.setFine(0);
                    record.setFineStatus("NONE");
                }

            } else {
                record.setFine(0);
                record.setFineStatus("NONE");
            }

            recordRepo.save(record);

            BookEntity book = bookRepo.findById(accessNo).orElse(null);
            if (book != null) {
                book.setStatus("AVAILABLE");
                bookRepo.save(book);
            }

            long activeBooks =recordRepo.countByMemberIdAndReturnDateIsNull(record.getMemberId());

            System.out.println(
                record.getRole() + " active books after return: " + activeBooks
            );

            return "Book returned successfully";

        } catch (Exception e) {
            e.printStackTrace();
            return "Return failed";
        }
    }
  
    //Pay fine
    public String payFine(Long recordId) {
        try {
            LibraryRecordEntity record = recordRepo.findById(recordId).orElse(null);

            if (record == null)
                return "Record not found";

            if (record.getFine() == 0)
                return "No fine to pay";

            record.setFineStatus("PAID");
            recordRepo.save(record);

            System.out.println("Fine paid for record " + recordId);
            return "Fine paid successfully";

        } catch (Exception e) {
            return "Error paying fine";
        }
    }
    
   //Get all library Records
    public List<LibraryRecordEntity> getAllRecords() {
        try {
            System.out.println("Fetching all library records");
            return recordRepo.findAll();
        } catch (Exception e) {
            System.out.println("Error fetching records");
            return List.of();
        }
    }

}
