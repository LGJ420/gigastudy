package com.example.gigastudy.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberPageController {
    
    @GetMapping("/login")
    public String loginPage(){

        return "member/login";
    }

    @GetMapping("/signup")
    public String signupPage(){

        return "member/signup";
    }

    @GetMapping("/complete")
    public String completePage(){

        return "member/complete";
    }

}
