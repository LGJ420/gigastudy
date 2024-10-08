package com.example.gigastudy.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/word")
public class WordPageController {
    
    @GetMapping("")
    public String mainPage(Model model) {

        model.addAttribute("contentTemplate", "");
        model.addAttribute("cssFile", "");
        model.addAttribute("jsFile", "");
        return "word/wordLayout";
    }

    @GetMapping("/kanji")
    public String kanjiPage(Model model) {

        model.addAttribute("contentTemplate", "word/kanji");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "kanji.js");
        return "word/wordLayout";
    }

    @GetMapping("/english")
    public String englishPage(Model model) {

        model.addAttribute("contentTemplate", "word/english");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "english.js");
        return "word/wordLayout";
    }

    @GetMapping("/kanji/saved")
    public String kanjiSavedPage(Model model) {

        model.addAttribute("contentTemplate", "word/kanjiSaved");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "kanjiSaved.js");
        return "word/wordLayout";
    }

    @GetMapping("/english/saved")
    public String EnglishSavedPage(Model model) {

        model.addAttribute("contentTemplate", "word/englishSaved");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "englishSaved.js");
        return "word/wordLayout";
    }




}
