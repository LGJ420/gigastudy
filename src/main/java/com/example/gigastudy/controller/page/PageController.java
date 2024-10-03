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

    @GetMapping("/kanji")
    public String kanjiPage(Model model) {

        model.addAttribute("contentTemplate", "kanji");
        model.addAttribute("cssFile", "card.css");
        model.addAttribute("jsFile", "kanji.js");
        return "index";
    }

    @GetMapping("/english")
    public String englishPage(Model model) {

        model.addAttribute("contentTemplate", "english");
        model.addAttribute("cssFile", "card.css");
        model.addAttribute("jsFile", "english.js");
        return "index";
    }

    @GetMapping("/save")
    public String savePage(Model model) {

        model.addAttribute("contentTemplate", "save");
        model.addAttribute("cssFile", "card.css");
        model.addAttribute("jsFile", "save.js");
        return "index";
    }




}
