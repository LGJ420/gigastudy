package com.example.gigastudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CardController {
    
    @GetMapping("card")
    public String getCard() {
        
        return "card/card";
    }
    
}
