package com.example.gigastudy.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gigastudy.entity.*;

public interface UserWordRepository extends JpaRepository<UserWord, Long>{
    
    Optional<UserWord> findByUserAndCard(User user, Word card);
}
