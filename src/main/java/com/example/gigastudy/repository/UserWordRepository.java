package com.example.gigastudy.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gigastudy.entity.*;

public interface UserWordRepository extends JpaRepository<UserWord, Long>{
    
    List<UserWord> findByUserAndWordTypeOrderBySeqAsc(User user, WordType type);
    Optional<UserWord> findByUserAndWord(User user, Word word);
}
