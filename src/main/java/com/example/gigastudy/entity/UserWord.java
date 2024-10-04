package com.example.gigastudy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserWord {

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

    // 암기 유무
    private Boolean flag;

    public void changeSeq(Long seq) {

        this.seq = seq;
    }

    public void changeFlag(Boolean flag) {

        this.flag = flag;
    }
}
