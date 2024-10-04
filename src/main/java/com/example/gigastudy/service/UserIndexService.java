package com.example.gigastudy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gigastudy.dto.UserIndexDTO;
import com.example.gigastudy.entity.*;
import com.example.gigastudy.repository.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
@RequiredArgsConstructor
public class UserIndexService {

    private final UserIndexRepository userIndexRepository;
    private final UserRepository userRepository;

    // 인덱스 불러오기
    public Long getIndex(Long userId, Boolean flag, WordType type) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        return userIndexRepository.findByUserAndFlagAndType(user, flag, type)
                .map(UserIndex::getSaveIndex) // 인덱스가 있으면 그 값을 반환
                .orElse(0L); // 없으면 0을 반환
    }

    // 인덱스 저장하기
    public void updateIndex(Long userId, UserIndexDTO userIndexDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        UserIndex userIndex = userIndexRepository.findByUserAndFlagAndType(user, userIndexDTO.getFlag(), userIndexDTO.getType())
                .orElse(UserIndex.builder()
                        .user(user)
                        .type(userIndexDTO.getType())
                        .flag(userIndexDTO.getFlag())
                        .saveIndex(userIndexDTO.getSaveIndex())
                        .build());

        userIndex.changeIndex(userIndexDTO.getSaveIndex());
        userIndexRepository.save(userIndex);
    }

}
