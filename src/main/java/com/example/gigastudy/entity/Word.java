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
        @UniqueConstraint(columnNames = {"word", "part"})
    }
)
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private String meaning;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WordType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WordPart part;
}
