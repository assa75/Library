package com.example.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librarymanagement.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity,Long> {

	long countByBookName(String bookName);
}
