package com.shoppingmall.controller;

import com.shoppingmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    //로그인 창 매핑
    @GetMapping(value = "/user/login")
    public String login(){
        return "user/login";
    }

    //회원가입 창 매핑
    @GetMapping(value = "/user/new")
    public String createForm(){
        return "user/createUserForm";
    }
}
