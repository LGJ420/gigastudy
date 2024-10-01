package com.example.gigastudy.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    
    private String username;
    private String password;
    private String nickname;
}
