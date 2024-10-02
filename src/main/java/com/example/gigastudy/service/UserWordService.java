package com.example.gigastudy.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gigastudy.dto.*;
import com.example.gigastudy.entity.*;
import com.example.gigastudy.repository.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
@RequiredArgsConstructor
public class UserWordService {
    
    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;


    // 단어 유형별 불러오기
    public List<UserWordDTO> getWordsByType(Long userId, WordType type){

        User user = userRepository.findById(userId)
            .orElseThrow(()->new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<UserWord> userWords = userWordRepository.findByUserAndWordTypeOrderBySeqAsc(user, type);


        return userWords.stream()
            .map(userWord -> UserWordDTO.builder()
                .wordDTO(WordDTO.builder()
                    .word(userWord.getWord().getWord())
                    .meaning(userWord.getWord().getMeaning())
                    .build())
                .seq(userWord.getSeq())
                .flag(userWord.getFlag())
                .build())
            .collect(Collectors.toList());
    }


    // 암기장 추가하기
    public void setFlag(Long userId, Long wordId){

        User user = userRepository.findById(userId)
            .orElseThrow(()->new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Word word = wordRepository.findById(wordId)
            .orElseThrow(()->new IllegalArgumentException("해당 단어가 존재하지 않습니다."));

        // 없으면 만들어서 새로 추가
        UserWord userWord = userWordRepository.findByUserAndWord(user, word)
            .orElse(UserWord.builder()
                    .user(user)
                    .word(word)
                    .flag(true)
                    .build());

        // 있으면 변경
        userWord.changeFlag(true);
        userWordRepository.save(userWord);
    }
}
