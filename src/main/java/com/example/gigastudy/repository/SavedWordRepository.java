package com.example.gigastudy.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gigastudy.entity.SavedWord;
import com.example.gigastudy.entity.User;
import com.example.gigastudy.entity.Word;
import com.example.gigastudy.entity.WordType;

public interface SavedWordRepository extends JpaRepository<SavedWord, Long>{
    
    Optional<SavedWord> findByUserAndWord(User user, Word word);

    void deleteByUserAndWord(User user, Word word);

    @Query("SELECT sw FROM SavedWord sw WHERE sw.user = :user AND sw.word.type = :type ORDER BY sw.seq")
    List<SavedWord> findByUserAndWordType(@Param("user") User user, @Param("type") WordType type);

    @Query("SELECT COALESCE(MAX(sw.seq), 0) FROM SavedWord sw WHERE sw.user = :user AND sw.word.type = :type")
    Long findMaxSeqByUserAndWordType(@Param("user") User user, @Param("type") WordType type);
}
