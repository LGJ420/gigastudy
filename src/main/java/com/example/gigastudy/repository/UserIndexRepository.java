package com.example.gigastudy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gigastudy.entity.*;

public interface UserIndexRepository extends JpaRepository<UserIndex, Long>{
    
    Optional<UserIndex> findByUserAndFlagAndType(User user, Boolean flag, WordType type);
}
