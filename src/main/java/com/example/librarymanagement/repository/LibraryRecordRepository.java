package com.example.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.librarymanagement.entity.LibraryRecordEntity;


public interface LibraryRecordRepository extends JpaRepository<LibraryRecordEntity,Long> {

    @Query("SELECT l FROM LibraryRecordEntity l WHERE l.accessNo = :accessNo AND l.returnDate IS NULL")
    LibraryRecordEntity findByAccessNoAndReturnDateIsNull(@Param("accessNo")Long accessNo);

    long countByMemberIdAndReturnDateIsNull(String memberId);

    @Query("SELECT l FROM LibraryRecordEntity l WHERE l.fineStatus = :status")
    List<LibraryRecordEntity> findByFineStatus(@Param("status")String status);
   
    List<LibraryRecordEntity> findAll();
}
