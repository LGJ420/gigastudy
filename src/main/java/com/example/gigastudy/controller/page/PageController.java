package com.example.gigastudy.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    
    @GetMapping("")
    public String mainPage(Model model) {

        model.addAttribute("contentTemplate", "");
        model.addAttribute("cssFile", "");
        model.addAttribute("jsFile", "");
        return "index";
    }

    @GetMapping("/test")
    public String testPage() {

        return "test/kanjitest";
    }

    @GetMapping("/kanji")
    public String kanjiPage(Model model) {

        model.addAttribute("contentTemplate", "kanji");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "kanji.js");
        return "index";
    }

    @GetMapping("/english")
    public String englishPage(Model model) {

        model.addAttribute("contentTemplate", "english");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "english.js");
        return "index";
    }

    @GetMapping("/kanji/saved")
    public String kanjiSavedPage(Model model) {

        model.addAttribute("contentTemplate", "kanjiSaved");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "kanjiSaved.js");
        return "index";
    }

    @GetMapping("/english/saved")
    public String EnglishSavedPage(Model model) {

        model.addAttribute("contentTemplate", "englishSaved");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "englishSaved.js");
        return "index";
    }




}
