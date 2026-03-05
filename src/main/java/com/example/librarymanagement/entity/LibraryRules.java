package com.example.librarymanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name="library_rules")
public class LibraryRules {
   
	@Id
	private String role;
	
	private int maxLimits;
	private int fine;
	private int dueDays;
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getMaxLimits() {
		return maxLimits;
	}
	public void setMaxLimits(int maxLimits) {
		this.maxLimits = maxLimits;
	}
	public int getFine() {
		return fine;
	}
	public void setFine(int fine) {
		this.fine = fine;
	}
	public int getDueDays() {
		return dueDays;
	}
	public void setDueDays(int dueDays) {
		this.dueDays = dueDays;
	}	
}
