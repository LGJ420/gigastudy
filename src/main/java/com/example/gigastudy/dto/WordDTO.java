package com.example.gigastudy.dto;

import com.example.gigastudy.entity.WordPart;
import com.example.gigastudy.entity.WordType;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordDTO {
    
    private Long id;
    private String word;
    private String mean1;
    private String mean2;
    private String mean3;

    private String pronounce;
    private String explain;

    private WordType type;
    private WordPart part;

    private String eword1;
    private String emean1;
    private String eword2;
    private String emean2;
    private String eword3;
    private String emean3;
    private String eword4;
    private String emean4;
}
