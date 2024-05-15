package com.shoppingmall.controller;

import com.shoppingmall.domain.LoginRequest;
import com.shoppingmall.domain.User;
import com.shoppingmall.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final UserService userService;

    public RestController(UserService userService) {
        this.userService = userService;
    }

    //로그인 처리
    @PostMapping(value = "/user/login")
    public String handleLogin(@RequestBody LoginRequest loginRequest){
        if(userService.Login(loginRequest)) return "success";
        else return "fail";
    }

    // 회원가입 처리
    @PostMapping(value = "/user/new")
    public String handleRequest(@RequestBody User user, RedirectAttributes redirectAttributes) {
        if (!userService.isIdExists(user.getId())) {
            userService.join(user);
            redirectAttributes.addAttribute("id", user.getId());
            return "success";
        }
        return "fail";
    }
}
