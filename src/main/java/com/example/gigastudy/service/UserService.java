package com.example.gigastudy.service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gigastudy.dto.UserDTO;
import com.example.gigastudy.entity.*;
import com.example.gigastudy.repository.*;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원가입
    public void signUp(UserDTO userDTO){

        if (userRepository.existsByUserId(userDTO.getUserId())) {

            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        // 비밀번호를 암호화하여 저장
        User user = User.builder()
            .userId(userDTO.getUserId())
            .userPw(passwordEncoder.encode(userDTO.getUserPw()))
            .nickname(userDTO.getNickname())
            .role(userDTO.getRole())
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
                .build();
            userWordRepository.save(userWord);
        });
    }
    
}
