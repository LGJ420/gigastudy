package com.example.gigastudy.dto;

import com.example.gigastudy.entity.WordType;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserIndexDTO {
    
    private WordType type;

    // 암기장이었는지 아니었는지 구분하기 위한 flag
    private boolean flag;

    // 어디까지 진행하였는지 기록
    private Long saveIndex;
}
