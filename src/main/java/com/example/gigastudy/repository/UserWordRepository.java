package com.example.gigastudy.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gigastudy.entity.*;

public interface UserWordRepository extends JpaRepository<UserWord, Long>{
    
    Optional<UserWord> findByUserAndWord(User user, Word word);

    List<UserWord> findByUserOrderBySeqAsc(User user);

    @Query("SELECT uw FROM UserWord uw WHERE uw.user = :user AND uw.word.type = :type ORDER BY uw.seq")
    List<UserWord> findByUserAndWordType(@Param("user") User user, @Param("type") WordType type);

    @Query("SELECT COUNT(uw) FROM UserWord uw WHERE uw.user = :user AND uw.word.type = :type AND uw.seq < (SELECT s.seq FROM UserWord s WHERE s.user = :user AND s.word = :word)")
    Optional<Long> findPositionByUserAndTypeAndWord(@Param("user") User user, @Param("type") WordType type, @Param("word") Word word);    
}
