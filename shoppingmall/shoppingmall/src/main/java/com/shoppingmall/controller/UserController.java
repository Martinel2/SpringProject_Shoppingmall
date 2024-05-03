package com.shoppingmall.controller;

import com.shoppingmall.domain.User;
import com.shoppingmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // 회원가입 처리
    @PostMapping(value = "/user/new")
    public String handleRequest(User user, RedirectAttributes redirectAttributes) {
        if (!userService.isIdExists(user.getId())) {
            userService.join(user);
            redirectAttributes.addAttribute("id", user.getId());
            return "redirect:/user/complete";
        }
        return "redirect:/";
    }

    // 회원가입 완료 페이지 매핑
    @GetMapping(value = "/user/complete")
    public String complete(@RequestParam("id") String id, Model model) {
        model.addAttribute("id", id);
        return "user/complete";
    }
}
