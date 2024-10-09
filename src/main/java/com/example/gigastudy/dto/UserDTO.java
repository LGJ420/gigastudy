package com.example.gigastudy.dto;

import java.util.Map;

import com.example.gigastudy.entity.UserRole;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    
    private String userId;
    private String userPw;
    private String nickname;
    private UserRole role;

    // JWT을 위해 DTO에서만 포함된다
    private Map<String, Object> claims;
}