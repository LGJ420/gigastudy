package com.example.gigastudy.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    
    @GetMapping("")
    public String getMain() {

        return "index";
    }
}
