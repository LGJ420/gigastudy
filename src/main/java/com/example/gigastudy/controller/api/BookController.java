package com.example.gigastudy.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gigastudy.service.WordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    
    private final WordService wordService;

    @GetMapping
    public ResponseEntity<?> getBookList(
        @RequestParam("type") String type){

        List<String> bookList = wordService.getBookList(type);

        return ResponseEntity.ok(bookList);
    }
}
