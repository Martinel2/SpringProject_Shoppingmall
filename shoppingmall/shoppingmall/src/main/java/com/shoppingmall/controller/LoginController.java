package com.shoppingmall.controller;

import com.shoppingmall.domain.Users;
import com.shoppingmall.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * [View] 로그인 페이지를 엽니다.
     */
    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    /**
     * [Action] 로그인 프로세스를 동작시킨다.
     */

    /*@PostMapping("/user/login")
    public String login(LoginRequest loginRequest) {
        boolean isValidMember = userSecurityService.isValidUser(loginRequest.getLoginId(), loginRequest.getPassword());
        if (isValidMember)
            return "/user/status";
        return "/user/login";
    }
    /**
     * [Action] 로그아웃 프로세스를 동작시킨다.
     */
    @GetMapping("/user/logout")
    public String logout(HttpServletResponse response) {
        // JWT 토큰을 저장하는 쿠키의 값을 삭제
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setMaxAge(0);  // 쿠키의 유효기간을 0으로 설정하여 즉시 삭제
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return "redirect:/login";  // 로그인 페이지로 리다이렉트
    }

    @PostMapping("/user/new")
    public ResponseEntity<String> registerUser(@RequestBody Users users) {
        Users savedUser = null;
        ResponseEntity response = null;
        try {
            String hashPwd = passwordEncoder.encode(users.getPassword());
            users.setPassword(hashPwd);
            if(userService.findById(users.getId()) == null) {
                savedUser = userService.join(users);
                if (savedUser.getId() != null) {
                    response = ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(Map.of("message", "success"));
                }
            }
            else{
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("중복된 ID입니다");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }
}
