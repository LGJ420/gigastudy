package com.example.gigastudy.service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gigastudy.dto.UserDTO;
import com.example.gigastudy.entity.*;
import com.example.gigastudy.repository.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;


    // 회원가입
    public void signUp(UserDTO userDTO){

        if (userRepository.existsByUsername(userDTO.getUsername())) {

            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        // 단순히 비밀번호를 저장, 스프링 시큐리티 설치후 수정필요
        User user = User.builder()
            .username(userDTO.getUsername())
            .password(userDTO.getPassword())
            .nickname(userDTO.getNickname())
            .build();

        userRepository.save(user);
        

        

        // 회원가입시 회원에게 모든 단어장 부여
        List<Word> allWords = wordRepository.findAll();
        
        // 회원에게 단어 부여하기전 단어 섞기
        Collections.shuffle(allWords);

        // 시퀀스 할당하기
        AtomicLong seq = new AtomicLong(1);

        allWords.forEach(word -> {
            UserWord userWord = UserWord.builder()
                .user(user)
                .word(word)
                .seq(seq.getAndIncrement())
                .flag(false)
                .build();
            userWordRepository.save(userWord);
        });
    }
    
}
