package com.example.gigastudy.controller.api;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gigastudy.entity.*;
import com.example.gigastudy.repository.UserRepository;
import com.example.gigastudy.repository.UserWordRepository;
import com.example.gigastudy.repository.WordRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/words")
public class WordController {
    
    private final WordRepository wordRepository;
    private final UserRepository userRepository;
    private final UserWordRepository userWordRepository;



    @GetMapping
    public List<Word> getWordsByPart(@RequestParam("type") WordType type) {

        return wordRepository.findByType(type);
    }



    @PostMapping("/{id}/flag")
    public ResponseEntity<String> setFlag(@PathVariable Long id, @RequestParam Long userId) {

        Word word = wordRepository.findById(id).orElseThrow(() -> new RuntimeException("Word not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        UserWord userWord = userWordRepository.findByUserAndCard(user, word)
                .orElse(UserWord.builder()
                        .user(user)
                        .word(word)
                        .flag(true)
                        .build());


        userWord.changeFlag(true);
        userWordRepository.save(userWord);

        return ResponseEntity.ok("Flag set to true for this word.");
    }

}
