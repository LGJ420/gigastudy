package com.example.gigastudy.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gigastudy.dto.UserIndexDTO;
import com.example.gigastudy.service.UserIndexService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userindex")
public class UserIndexController {

    private final UserIndexService userIndexService;

    // 인덱스 불러오기
    @GetMapping
    public ResponseEntity<?> getIndex(@RequestBody UserIndexDTO userIndexDTO){

        // 현재 유저아이디 1로 고정
        Long userId = 1L;

        Long userIndex = userIndexService.getIndex(userId, userIndexDTO);

        return ResponseEntity.ok(userIndex);
    }

    // 인덱스 저장하기
    @PostMapping
    public ResponseEntity<?> updateIndex(@RequestBody UserIndexDTO userIndexDTO) {

        // 현재 유저아이디 1로 고정
        Long userId = 1L;

        userIndexService.updateIndex(userId, userIndexDTO);

        return ResponseEntity.ok("인덱스가 저장되었습니다.");
    }

}
