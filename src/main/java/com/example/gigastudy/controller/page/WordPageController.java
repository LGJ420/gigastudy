package com.example.gigastudy.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/word")
public class WordPageController {
    
    // 일반단어장
    @GetMapping("/kanji")
    public String kanjiPage(Model model) {

        model.addAttribute("contentTemplate", "word/kanji");
        model.addAttribute("cssFile", "wordTwoCard.css");
        model.addAttribute("jsFile", "kanji.js");
        return "word/wordExtendTurnLayout";
    }

    @GetMapping("/japanese1")
    public String japanese1Page(Model model) {

        model.addAttribute("contentTemplate", "word/japanese1");
        model.addAttribute("cssFile", "wordTwoCard.css");
        model.addAttribute("jsFile", "japanese1.js");
        
        return "word/wordSimplePassLayout";
    }

    @GetMapping("/japanese2")
    public String japanese2Page(Model model) {

        model.addAttribute("contentTemplate", "word/japanese2");
        model.addAttribute("cssFile", "wordTwoCard.css");
        model.addAttribute("jsFile", "japanese2.js");
        
        return "word/wordSimplePassLayout";
    }

    @GetMapping("/english")
    public String englishPage(Model model) {

        model.addAttribute("contentTemplate", "word/englishOrigin");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "englishOrigin.js");
        return "word/wordNormalTurnLayout";
    }

    @GetMapping("/english_easy1")
    public String englishEasy1Page(Model model) {

        model.addAttribute("contentTemplate", "word/englishEasy1");
        model.addAttribute("cssFile", "wordTwoCard.css");
        model.addAttribute("jsFile", "englishEasy1.js");
        
        return "word/wordExtendTurnLayout";
    }

    @GetMapping("/english_easy2")
    public String englishEasy2Page(Model model) {

        model.addAttribute("contentTemplate", "word/englishEasy2");
        model.addAttribute("cssFile", "wordTwoCard.css");
        model.addAttribute("jsFile", "englishEasy2.js");
        
        return "word/wordExtendTurnLayout";
    }




    // 암기단어장
    @GetMapping("/kanji/saved")
    public String kanjiSavedPage(Model model) {

        model.addAttribute("contentTemplate", "word/kanjiSaved");
        model.addAttribute("cssFile", "wordTwoCard.css");
        model.addAttribute("jsFile", "kanjiSaved.js");
        return "word/wordExtendTurnLayout";
    }

    @GetMapping("/japanese1/saved")
    public String japanese1SavedPage(Model model) {

        model.addAttribute("contentTemplate", "word/japanese1Saved");
        model.addAttribute("cssFile", "wordTwoCard.css");
        model.addAttribute("jsFile", "japanese1Saved.js");
        
        return "word/wordSimplePassLayout";
    }

    @GetMapping("/japanese2/saved")
    public String japanese2SavedPage(Model model) {

        model.addAttribute("contentTemplate", "word/japanese2Saved");
        model.addAttribute("cssFile", "wordTwoCard.css");
        model.addAttribute("jsFile", "japanese2Saved.js");
        
        return "word/wordSimplePassLayout";
    }

    @GetMapping("/english/saved")
    public String EnglishSavedPage(Model model) {

        model.addAttribute("contentTemplate", "word/englishOriginSaved");
        model.addAttribute("cssFile", "word.css");
        model.addAttribute("jsFile", "englishOriginSaved.js");
        return "word/wordNormalTurnLayout";
    }

    @GetMapping("/english_easy1/saved")
    public String englishEasy1SavedPage(Model model) {

        model.addAttribute("contentTemplate", "word/englishEasy1Saved");
        model.addAttribute("cssFile", "wordTwoCard.css");
        model.addAttribute("jsFile", "englishEasy1Saved.js");
        
        return "word/wordExtendTurnLayout";
    }

    @GetMapping("/english_easy2/saved")
    public String englishEasy2SavedPage(Model model) {

        model.addAttribute("contentTemplate", "word/englishEasy2Saved");
        model.addAttribute("cssFile", "wordTwoCard.css");
        model.addAttribute("jsFile", "englishEasy2Saved.js");
        
        return "word/wordExtendTurnLayout";
    }
}
