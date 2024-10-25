package com.example.gigastudy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserIndex {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private WordType type;

    // 암기장이었는지 아니었는지 구분하기 위한 flag
    private boolean flag;

    // 어디까지 진행하였는지 기록
    private Long saveIndex;

    public void changeIndex(Long saveIndex) {

        this.saveIndex = saveIndex;
    }
}
