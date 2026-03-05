package com.example.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.librarymanagement.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.rollNo = :rollNo")
    UserEntity findByRollNo(@Param("rollNo")String rollNo);

    @Query("SELECT u FROM UserEntity u WHERE u.facultyId = :facultyId")
    UserEntity findByFacultyId(@Param("facultyId")String facultyId);
}
