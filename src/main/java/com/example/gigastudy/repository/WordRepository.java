package com.example.gigastudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gigastudy.entity.Word;

public interface WordRepository extends JpaRepository<Word, Long>{

    List<Word> findByTypeIn(List<String> types);

    // 소분류의 종류를 반환함, 책 목록 보여주기에 사용하는 용도
    // '-'는 JPT 분류에 숫자 오름차순을 인식시키기 위함
    @Query(value = "SELECT DISTINCT subcategory " +
                   "FROM word " +
                   "WHERE type = :type " +
                   "ORDER BY " +
                   "CAST(SUBSTRING_INDEX(subcategory, '-', 1) AS UNSIGNED), " +
                   "CAST(SUBSTRING_INDEX(subcategory, '-', -1) AS UNSIGNED)", 
           nativeQuery = true)
    List<String> bookList(@Param("type") String type);
}
