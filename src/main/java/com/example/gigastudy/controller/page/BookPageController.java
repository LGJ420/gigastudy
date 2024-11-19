package com.example.gigastudy.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.gigastudy.service.WordService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookPageController {
    
    private final WordService wordService;

    
}
