package com.example.gigastudy.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    
    @GetMapping
    public String indexPage(){

        return "index";
    }

    @GetMapping("home")
    public String homePage(){

        return "redirect:/";
    }

    @GetMapping("test")
    public String testPage() {

        return "test/test";
    }

}
