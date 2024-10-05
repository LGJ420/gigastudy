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
    public ResponseEntity<?> getWords(
            @RequestParam(value = "flag", required = false) Boolean flag,
            @RequestParam(value = "type", required = false) WordType type) {

        // 현재 유저아이디 1로 고정
        Long userId = 1L;

        List<UserWordDTO> userWordDTOs = userWordService.getWords(userId, flag, type);

        return ResponseEntity.ok(userWordDTOs);
    }

    // 암기장에 단어 저장하기
    @PostMapping("/{wordId}")
    public ResponseEntity<?> saveFlag(@PathVariable Long wordId) {

        // 현재 유저아이디 1로 고정
        Long userId = 1L;

        userWordService.saveFlag(userId, wordId);

        return ResponseEntity.ok("암기장에 단어가 저장되었습니다.");
    }

    // 암기장에서 단어 삭제하기
    @DeleteMapping("/{wordId}")
    public ResponseEntity<?> deleteFlag(@PathVariable Long wordId) {

        // 현재 유저아이디 1로 고정
        Long userId = 1L;

        userWordService.deleteFlag(userId, wordId);

        return ResponseEntity.ok("암기장에서 단어가 삭제되었습니다.");
    }

    // 단어 섞기
    @PostMapping("/shuffle")
    public ResponseEntity<?> shuffleWords() {

        // 현재 유저아이디 1로 고정
        Long userId = 1L;

        userWordService.shuffleWords(userId);

        return ResponseEntity.ok("단어가 성공적으로 섞였습니다.");
    }
}