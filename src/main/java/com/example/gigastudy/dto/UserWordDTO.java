package com.example.gigastudy.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWordDTO {
    
    private WordDTO wordDTO;

    // 셔플용 숫자
    private Long seq;
}