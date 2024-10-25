package com.example.gigastudy.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gigastudy.entity.*;

public interface UserWordRepository extends JpaRepository<UserWord, Long>{
    
    Optional<UserWord> findByUserAndWord(User user, Word word);

    @Query("SELECT uw FROM UserWord uw WHERE uw.user = :user AND uw.word.type = :type")
    List<UserWord> findByUserAndWordType(@Param("user") User user, @Param("type") WordType type);
}
