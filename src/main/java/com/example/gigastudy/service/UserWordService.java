package com.example.gigastudy.service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gigastudy.dto.*;
import com.example.gigastudy.entity.*;
import com.example.gigastudy.repository.*;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class UserWordService {

    private final UserRepository userRepository;
    private final UserWordRepository userWordRepository;
    private final UserIndexRepository userIndexRepository;

    // 일반 단어 유형별 불러오기
    public List<UserWordDTO> getWords(Long userId, WordType wordType) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<UserWord> userWords;

        if (wordType != null) {

            userWords = userWordRepository.findByUserAndWordType(user, wordType);
        } else {

            userWords = userWordRepository.findByUserOrderBySeqAsc(user);
        }

        return userWords.stream()
                .map(userWord -> UserWordDTO.builder()
                        .wordDTO(WordDTO.builder()
                                .id(userWord.getWord().getId())
                                .word(userWord.getWord().getWord())
                                .mean1(userWord.getWord().getMean1())
                                .build())
                        .seq(userWord.getSeq())
                        .build())
                .collect(Collectors.toList());
    }

    // 일반 단어 섞기
    public void shuffleWords(Long userId, WordType wordType) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<UserWord> userWords = userWordRepository.findByUserAndWordType(user, wordType);

        // 시퀀스 재할당
        Collections.shuffle(userWords);

        AtomicLong seq = new AtomicLong(1);

        userWords.forEach(userWord -> {
            userWord.changeSeq(seq.getAndIncrement());
            userWordRepository.save(userWord);
        });

        // 인덱스 초기화
        UserIndex userIndex = userIndexRepository.findByUserAndFlagAndType(user, false, wordType)
                .orElseGet(() -> UserIndex.builder()
                        .user(user)
                        .flag(false)
                        .type(wordType)
                        .saveIndex(0L)
                        .build());

        userIndex.changeIndex(0L);
        userIndexRepository.save(userIndex);
    }
}
