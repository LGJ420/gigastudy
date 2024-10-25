package com.example.gigastudy.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.gigastudy.dto.UserIndexDTO;
import com.example.gigastudy.entity.User;
import com.example.gigastudy.entity.WordType;
import com.example.gigastudy.service.UserIndexService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userindex")
public class UserIndexController {

    private final UserIndexService userIndexService;

    // 현재 로그인한 유저의 아이디를 가져오는 메서드
    private Long getCurrentUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    // 인덱스 불러오기
    @GetMapping
    public ResponseEntity<?> getIndex(
            @RequestParam("flag") boolean flag,
            @RequestParam("type") WordType type) {

        Long userId = getCurrentUserId();

        Long userIndex = userIndexService.getIndex(userId, flag, type);

        return ResponseEntity.ok(userIndex);
    }

    // 인덱스 저장하기
    @PostMapping
    public ResponseEntity<?> updateIndex(@RequestBody UserIndexDTO userIndexDTO) {

        Long userId = getCurrentUserId();

        userIndexService.updateIndex(userId, userIndexDTO);

        return ResponseEntity.ok("인덱스가 저장되었습니다.");
    }

}
