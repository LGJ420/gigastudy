package com.example.gigastudy.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CardPageController {
    
    @GetMapping("card")
    public String getCard() {
        
        return "card";
    }
}
