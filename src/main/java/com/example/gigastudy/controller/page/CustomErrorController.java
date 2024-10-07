package com.example.gigastudy.controller.page;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletResponse;

// 현재 계속 200에 대한 처리만 발생중, 오류 수정 필요
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletResponse response, Model model) {

    int statusCode = response.getStatus();

    String errorTitle;
    String errorMessage;

    // 상태 코드에 따라 다른 메시지 설정
    switch (statusCode) {
        case 404:
            errorTitle = "죄송합니다";
            errorMessage = "사이트 준비중입니다.";
            break;
        case 500:
            errorTitle = "경고";
            errorMessage = "잘못된 접근방식입니다.";
            break;
        default:
            errorTitle = "죄송합니다";
            errorMessage = "사이트 준비중입니다.";
            break;
    }

    model.addAttribute("errorTitle", errorTitle);
    model.addAttribute("errorMessage", errorMessage);

    return "error/error";
}

}
