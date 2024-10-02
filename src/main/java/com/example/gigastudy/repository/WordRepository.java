package com.example.gigastudy.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gigastudy.entity.Word;
import com.example.gigastudy.entity.WordType;

public interface WordRepository extends JpaRepository<Word, Long>{

}
