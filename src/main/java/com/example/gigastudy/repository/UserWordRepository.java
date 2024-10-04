package com.example.gigastudy.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gigastudy.entity.*;

public interface UserWordRepository extends JpaRepository<UserWord, Long>{
    
    List<UserWord> findByUserAndFlagOrderBySeqAsc(User user, Boolean flag);
    List<UserWord> findByUserAndWord_TypeOrderBySeqAsc(User user, WordType type);
    List<UserWord> findByUserAndFlagAndWord_TypeOrderBySeqAsc(User user, Boolean flag, WordType type);
    Optional<UserWord> findByUserAndWord(User user, Word word);
}
