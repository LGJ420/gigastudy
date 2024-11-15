package com.example.gigastudy.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/word")
public class WordPageController {
    
    @GetMapping("/kanji")
    public String kanjiPage(Model model) {

        model.addAttribute("contentTemplate", "word/kanji");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "kanji.js");
        return "word/wordNormalTurnLayout";
    }

    @GetMapping("/english")
    public String englishPage(Model model) {

        model.addAttribute("contentTemplate", "word/englishOrigin");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "englishOrigin.js");
        return "word/wordNormalTurnLayout";
    }

    @GetMapping("/kanji/saved")
    public String kanjiSavedPage(Model model) {

        model.addAttribute("contentTemplate", "word/kanjiSaved");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "kanjiSaved.js");
        return "word/wordNormalTurnLayout";
    }

    @GetMapping("/english/saved")
    public String EnglishSavedPage(Model model) {

        model.addAttribute("contentTemplate", "word/englishOriginSaved");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "englishOriginSaved.js");
        return "word/wordNormalTurnLayout";
    }
}
