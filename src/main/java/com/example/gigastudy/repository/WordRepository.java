package com.example.gigastudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gigastudy.entity.Word;

public interface WordRepository extends JpaRepository<Word, Long>{

}
