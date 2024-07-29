package com.shoppingmall.controller;

import com.shoppingmall.domain.Users;
import com.shoppingmall.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    // 로그인 창 매핑
    @GetMapping(value = "/user/login")
    public String login() {
        return "user/login";
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

    /*
    @GetMapping(value = "/user/status")
    public String gotoStatusPage(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session == null){
            return "user/login";
        }
        String id = (String)session.getAttribute(SessionConst.sessionId);
        Optional<Users> findUserOptional = Optional.ofNullable(userService.findById(id));
        Users user = findUserOptional.orElse(null);
        if(user == null)
            return "user/login";
        model.addAttribute("user", user);
        return "user/status";
    }
    */
    @PostMapping("logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            return "redirect:/";
        }
        session.invalidate(); //세션종료
        return "redirect:/";
    }
}
