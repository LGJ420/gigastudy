package com.example.gigastudy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
    // 복합제약조건
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"word", "type", "part", "subcategory"})
    }
)
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private String mean1;
    private String mean2;
    private String mean3;

    private String pronounce;
    private Long importance;
    private String description;
    private String subcategory;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WordType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
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
