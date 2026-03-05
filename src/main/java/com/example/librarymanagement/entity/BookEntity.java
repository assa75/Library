package com.example.librarymanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name="books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessNo;

    private String bookName;
    private String status;
    
	public long getAccessNo() {
		return accessNo;
	}
	public void setAccessNo(long accessNo) {
		this.accessNo = accessNo;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	} 
}

