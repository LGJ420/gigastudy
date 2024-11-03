// package com.example.gigastudy.controller.api;

// import org.springframework.web.bind.annotation.RestController;

// import com.example.gigastudy.dto.UserDTO;
// import com.example.gigastudy.service.UserService;

// import lombok.RequiredArgsConstructor;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;


// @RestController
// @RequiredArgsConstructor
// @RequestMapping("/api/user")
// public class UserController {
    
//     private final UserService userService;

//     @PostMapping
//     public ResponseEntity<String> signUp(@RequestBody UserDTO userDTO) {
        
//         userService.signUp(userDTO);

//         return ResponseEntity.ok("회원가입이 완료되었습니다.");
//     }
    
// }
