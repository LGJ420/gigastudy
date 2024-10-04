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
import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
@RequiredArgsConstructor
public class UserWordService {

    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;
    private final UserIndexRepository userIndexRepository;

    // 단어 유형별 불러오기
    public List<UserWordDTO> getWords(Long userId, Boolean flag, WordType type) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<UserWord> userWords;

        if (flag != null && type != null) {
            // flag와 type 둘 다 있는 경우
            userWords = userWordRepository.findByUserAndFlagAndWord_TypeOrderBySeqAsc(user, flag, type);
        } else if (flag != null) {
            // flag만 있는 경우
            userWords = userWordRepository.findByUserAndFlagOrderBySeqAsc(user, flag);
        } else if (type != null) {
            // type만 있는 경우
            userWords = userWordRepository.findByUserAndWord_TypeOrderBySeqAsc(user, type);
        } else {
            // 아무 조건도 없는 경우 (필요하면 추가)
            userWords = userWordRepository.findAll();
        }

        return userWords.stream()
                .map(userWord -> UserWordDTO.builder()
                        .wordDTO(WordDTO.builder()
                                .id(userWord.getWord().getId())
                                .word(userWord.getWord().getWord())
                                .meaning(userWord.getWord().getMeaning())
                                .build())
                        .seq(userWord.getSeq())
                        .flag(userWord.getFlag())
                        .build())
                .collect(Collectors.toList());
    }




    // 암기장 추가하기
    public void setFlag(Long userId, Long wordId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new IllegalArgumentException("해당 단어가 존재하지 않습니다."));

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




    // 단어 섞기
    public void shuffleWords(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<UserWord> userWords = userWordRepository.findByUserOrderBySeqAsc(user);

        // 시퀀스 재할당
        Collections.shuffle(userWords);

        AtomicLong seq = new AtomicLong(1);

        userWords.forEach(userWord -> {
            userWord.changeSeq(seq.getAndIncrement());
            userWordRepository.save(userWord);
        });

        // 인덱스 초기화
        List<UserIndex> userIndexs = userIndexRepository.findByUser(user);

        userIndexs.forEach(userIndex -> {
            userIndex.changeIndex(0L);
            userIndexRepository.save(userIndex);
        });
    }
}
