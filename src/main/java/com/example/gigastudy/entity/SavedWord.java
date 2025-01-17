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
        @UniqueConstraint(columnNames = {"user_id", "word_id"})
    }
)
public class SavedWord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    // 셔플용 숫자
    private Long seq;

    public void changeSeq(Long seq) {

        this.seq = seq;
    }
}
