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
public class SavedWordService {

    private final SavedWordRepository savedWordRepository;
    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final UserIndexRepository userIndexRepository;

    // 암기장 단어 유형별 불러오기
    public List<UserWordDTO> getSavedWords(Long userId, WordType wordType) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<SavedWord> savedWords;

        if (wordType != null) {

            savedWords = savedWordRepository.findByUserAndWordType(user, wordType);
        } else {

            savedWords = savedWordRepository.findByUserOrderBySeqAsc(user);
        }

        return savedWords.stream()
                .map(userWord -> UserWordDTO.builder()
                        .wordDTO(WordDTO.builder()
                                .id(userWord.getWord().getId())
                                .word(userWord.getWord().getWord())
                                .mean1(userWord.getWord().getMean1())
                                .mean2(userWord.getWord().getMean2())
                                .mean3(userWord.getWord().getMean3())
                                .pronounce(userWord.getWord().getPronounce())
                                .importance(userWord.getWord().getImportance())
                                .description(userWord.getWord().getDescription())
                                .subcategory(userWord.getWord().getSubcategory())
                                .type(userWord.getWord().getType())
                                .part(userWord.getWord().getPart())
                                .eword1(userWord.getWord().getEword1())
                                .emean1(userWord.getWord().getEmean1())
                                .eword2(userWord.getWord().getEword2())
                                .emean2(userWord.getWord().getEmean2())
                                .eword3(userWord.getWord().getEword3())
                                .emean3(userWord.getWord().getEmean3())
                                .eword4(userWord.getWord().getEword4())
                                .emean4(userWord.getWord().getEmean4())
                                .eword5(userWord.getWord().getEword5())
                                .emean5(userWord.getWord().getEmean5())
                                .build())
                        .seq(userWord.getSeq())
                        .build())
                .collect(Collectors.toList());
    }

    // 암기장 단어 섞기
    public void shuffleSavedWords(Long userId, WordType wordType) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<SavedWord> savedWords = savedWordRepository.findByUserAndWordType(user, wordType);

        // 시퀀스 재할당
        Collections.shuffle(savedWords);

        AtomicLong seq = new AtomicLong(1);

        savedWords.forEach(savedWord -> {
            savedWord.changeSeq(seq.getAndIncrement());
            savedWordRepository.save(savedWord);
        });

        Long firstWordId = savedWords.isEmpty() ? 0L : savedWords.get(0).getWord().getId();

        // 인덱스 초기화
        UserIndex userIndex = userIndexRepository.findByUserAndFlagAndType(user, true, wordType)
                .orElseGet(() -> UserIndex.builder()
                        .user(user)
                        .flag(true)
                        .type(wordType)
                        .saveIndex(0L)
                        .build());

        userIndex.changeIndex(firstWordId);
        userIndexRepository.save(userIndex);
    }

    // 암기장에 단어 저장하기
    public void saveWord(Long userId, Long wordId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new IllegalArgumentException("해당 단어가 존재하지 않습니다."));

        // 순서대로 저장시키기 위해 마지막 번호 찾기
        Long maxSeq = savedWordRepository.findMaxSeqByUserAndWordType(user, word.getType());

        // 없으면 만들어서 새로 만듬
        savedWordRepository.findByUserAndWord(user, word)
                .orElse(savedWordRepository.save(
                        SavedWord.builder()
                                .user(user)
                                .word(word)
                                .seq(maxSeq + 1)
                                .build()));
    }

    // 암기장에서 단어 삭제하기
    public void deleteSavedWord(Long userId, Long wordId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new IllegalArgumentException("해당 단어가 존재하지 않습니다."));

        savedWordRepository.deleteByUserAndWord(user, word);
    }
}
