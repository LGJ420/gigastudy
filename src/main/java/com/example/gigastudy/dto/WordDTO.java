package com.example.gigastudy.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordDTO {
    
    private String word;
    private String meaning;
}
