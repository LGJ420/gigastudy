package com.example.gigastudy.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gigastudy.dto.UserIndexDTO;
import com.example.gigastudy.entity.*;
import com.example.gigastudy.repository.*;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class UserIndexService {

    private final UserIndexRepository userIndexRepository;
    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;
    private final SavedWordRepository savedWordRepository;

    


    // 인덱스 불러오기
    public Long getIndex(Long userId, boolean flag, WordType type) {

        Long position;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Optional<Long> optionalSaveIndex = userIndexRepository.findByUserAndFlagAndType(user, flag, type)
                .map(UserIndex::getSaveIndex); // 인덱스가 있으면 그 값을 반환

        if (optionalSaveIndex.isPresent()) {
            Long saveIndex = optionalSaveIndex.get();

            Word findWord = wordRepository.findById(saveIndex)
                    .orElseThrow(() -> new IllegalArgumentException("단어를 찾을 수 없습니다."));

            if (flag) {
                position = savedWordRepository.findPositionByUserAndTypeAndWord(user, type, findWord)
                        .orElse(0L);
            } else {
                position = userWordRepository.findPositionByUserAndTypeAndWord(user, type, findWord)
                        .orElse(0L);
            }
        } else {
            position = 0L;
        }

        return position;
    }





    // 인덱스 저장하기
    public void updateIndex(Long userId, UserIndexDTO userIndexDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        UserIndex userIndex = userIndexRepository
                .findByUserAndFlagAndType(user, userIndexDTO.isFlag(), userIndexDTO.getType())
                .orElseGet(() -> UserIndex.builder()
                        .user(user)
                        .type(userIndexDTO.getType())
                        .flag(userIndexDTO.isFlag())
                        .saveIndex(userIndexDTO.getSaveIndex())
                        .build());

        userIndex.changeIndex(userIndexDTO.getSaveIndex());
        userIndexRepository.save(userIndex);
    }

}
