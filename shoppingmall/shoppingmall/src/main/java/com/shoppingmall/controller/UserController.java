package com.shoppingmall.controller;

import com.shoppingmall.domain.Users;
import com.shoppingmall.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // 회원가입 창 매핑
    @GetMapping(value = "/user/new")
    public String createForm() {
        return "user/createUserForm";
    }

    // 회원가입 완료 페이지 매핑
    @GetMapping(value = "/user/complete")
    public String complete(@RequestParam(value = "id", required = false) String id, Model model) {
        model.addAttribute("id", id);
        return "user/complete";
    }

    @GetMapping("/user/status")
    public String dashboardPage(@AuthenticationPrincipal User user, Model model) {
        Users users = userService.findById(user.getUsername());
        model.addAttribute("user", users);
        return "/user/status";
    }

}
