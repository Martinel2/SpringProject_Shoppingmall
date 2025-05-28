package com.shoppingmall.Email.Controller;

import com.shoppingmall.User.Domain.Users;
import com.shoppingmall.Global.Auth.Dto.FindPwDto;
import com.shoppingmall.Email.Service.EmailService;
import com.shoppingmall.User.Service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    private final UserService userService;

    @GetMapping("/find_pw")
    public String pwMail(){
        return "/login/find_pw";
    }

    @PostMapping("/find_pw")
    public ResponseEntity<FindPwDto> sendResetEmail(@RequestParam(name = "id") String id, HttpSession session) throws MessagingException, UnsupportedEncodingException {
        FindPwDto response;
        try {
            String title = "비밀번호 인증 코드";
            Users user = userService.findById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new FindPwDto("아이디가 존재하지 않습니다.", null));
            }

            String email = user.getEmail();
            if (email == null || !email.contains("@")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new FindPwDto("등록된 이메일이 없습니다.", null));
            }
            emailService.sendEmail(email,title);
            session.setAttribute("email", email); // 이메일을 세션에 저장
            response = new FindPwDto("인증 이메일이 발송되었습니다.", email);
            return ResponseEntity.ok(response); // 200 OK 응답 반환

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new FindPwDto("아이디가 존재하지 않습니다.", null)); // 500 응답 반환
        }
    }

    @GetMapping("/verify_code")
    public String verifyPage(){
        return "/login/verify";
    }

    @PostMapping("/verify_code")
    public ResponseEntity<String> verifyAuthCode(@RequestParam(name="authCode") String authCode,HttpSession session) {
        ResponseEntity response;
        try {
            String email = (String) session.getAttribute("email");
            boolean isValid = emailService.verifyAuthCode(email, authCode);
            if (isValid) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("인증 코드가 확인되었습니다. 비밀번호 재설정 페이지로 이동합니다.");
            } else {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("인증 코드가 유효하지 않습니다.");
            }

        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("아이디가 존재하지 않습니다.");
        }
        return response;
    }
}