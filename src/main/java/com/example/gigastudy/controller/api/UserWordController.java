package com.example.gigastudy.controller.api;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gigastudy.dto.UserWordDTO;
import com.example.gigastudy.entity.*;
import com.example.gigastudy.service.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userword")
public class UserWordController {
    
    private final UserWordService userWordService;


    // 단어 유형별 불러오기
    @GetMapping
    public ResponseEntity<?> getWordsByType(@RequestParam("type") WordType type) {

        // 현재 유저아이디 1로 고정
        Long userId = 1L;

        List<UserWordDTO> userWordDTOs =  userWordService.getWordsByType(userId, type);

        return ResponseEntity.ok(userWordDTOs);
    }



    // 암기장 추가하기
    @PostMapping("/{wordId}")
    public ResponseEntity<?> setFlag(@PathVariable Long wordId) {

        // 현재 유저아이디 1로 고정
        Long userId = 1L;

        userWordService.setFlag(userId, wordId);

        return ResponseEntity.ok("암기장에 저장되었습니다.");
    }

}