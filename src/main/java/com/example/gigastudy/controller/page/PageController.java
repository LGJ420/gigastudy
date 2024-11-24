package com.example.gigastudy.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    
    @GetMapping
    public String indexPage(){

        return "index";
    }

    @GetMapping("test1")
    public String test1Page(Model model) {

        model.addAttribute("contentTemplate", "word/englishEasy1");
        model.addAttribute("cssFile", "wordTwoCard.css");
        model.addAttribute("jsFile", "englishEasy1.js");
        
        return "word/wordExtendTurnLayout";
    }

    @GetMapping("test2")
    public String test2Page(Model model) {

        model.addAttribute("contentTemplate", "word/japanese1");
        model.addAttribute("cssFile", "wordTwoCard.css");
        model.addAttribute("jsFile", "japanese1.js");
        
        return "word/wordSimplePassLayout";
    }
}
