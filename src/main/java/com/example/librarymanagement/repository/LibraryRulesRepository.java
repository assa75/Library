package com.example.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.librarymanagement.entity.LibraryRules;


public interface LibraryRulesRepository extends JpaRepository<LibraryRules,String> {
	

}
