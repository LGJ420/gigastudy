package com.example.gigastudy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gigastudy.repository.WordRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class WordService {
    
    private final WordRepository wordRepository;

    public List<String> getBookList(String type){

        return wordRepository.bookList(type);
    }
}
