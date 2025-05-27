package com.shoppingmall.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupDto {
        private String id;
        private String password;
        private String name;
        private String birth;
        private String email;
        private String phone;
        private String place;
        private String enabled;
        private String role;
        private String sex;

        // 기본 생성자 (필수)
        public SignupDto() {}
}
