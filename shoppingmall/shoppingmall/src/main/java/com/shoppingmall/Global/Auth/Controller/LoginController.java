package com.shoppingmall.Global.Auth.Controller;

import com.shoppingmall.Global.Response.Result.ResultCode;
import com.shoppingmall.Global.Response.Result.ResultResponse;
import com.shoppingmall.User.Domain.Users;
import com.shoppingmall.Global.Auth.Dto.SignupDto;
import com.shoppingmall.User.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Slf4j
@Controller
public class LoginController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public LoginController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "/login/login";
    }

    @GetMapping("/user/logout")
    public String logout(HttpServletResponse response) {
        // JWT 토큰을 저장하는 쿠키의 값을 삭제
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setMaxAge(0);  // 쿠키의 유효기간을 0으로 설정하여 즉시 삭제
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return "redirect:/login";  // 로그인 페이지로 리다이렉트
    }

    @PostMapping("/usernew")
    public ResponseEntity<ResultResponse> registerUser(@RequestBody SignupDto signupDto) {
        Users users = userService.signUp(signupDto);
        final ResultResponse response = new ResultResponse(ResultCode.REGISTER_SUCCESS,users);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @PostMapping("/dup_check")
    public ResponseEntity<ResultResponse> dupCheck(@RequestBody Map<String, String> body) {
        userService.dupCheck(body.get("id"));
        final ResultResponse response = new ResultResponse(ResultCode.DUP_CHECK_SUCCESS,body.get("id"));
        return new ResponseEntity<>(response,response.getStatus());
    }

    @GetMapping("/find_id")
    public String findIdPage() {
        return "/login/findId";
    }

    @PostMapping("/find_id")
    public ResponseEntity<String> findIdByEmail(@RequestParam(name = "email") String email){
        ResponseEntity response = null;
        try {
            Users user = userService.findByEmail(email);
            String id = user.getId();
            response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(id);

        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("아이디가 존재하지 않습니다.");
        }
        return response;
    }

    @GetMapping("/reset_password")
    public String pwResetPage(){
        return "/login/pwReset";
    }

    @PostMapping("/resetPW")
    public ResponseEntity<String> resetPassword(@RequestParam(name = "newPassword") String newPassword, HttpSession session) {
        ResponseEntity response;
        try {
            String email = (String) session.getAttribute("email");
            Users user = userService.findByEmail(email);
            user.setPassword(passwordEncoder.encode(newPassword));
            response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("비밀번호 재설정이 완료되었습니다.");

        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("아이디가 존재하지 않습니다.");
        }
        return response;
    }
}
