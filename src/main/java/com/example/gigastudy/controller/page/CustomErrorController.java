package com.example.gigastudy.controller.page;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    // 현재는 error.html페이지 하나만 사용중이다.
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        // 상태 코드 확인하여 적절한 에러 페이지 반환
        Object statusCodeObj = request.getAttribute("javax.servlet.error.status_code");

        // statusCode가 null인 경우 기본 에러 페이지 반환
        if (statusCodeObj == null) {
            return "error/error";
        }

        Integer statusCode = (Integer) statusCodeObj;

        // 404 에러 페이지
        if (statusCode == 404) {
            return "error/error";
        }
        // 500 에러 페이지
        else if (statusCode == 500) {
            return "error/error";
        }
        // 그 외 에러 페이지
        else {
            return "error/error";
        }
    }
}