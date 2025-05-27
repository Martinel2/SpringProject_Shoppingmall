package com.shoppingmall.Global.Response.Result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResultCode {

    // Member
    DUP_CHECK_SUCCESS(HttpStatus.OK, "M000", "사용가능한 아이디입니다."),
    REGISTER_SUCCESS(HttpStatus.CREATED, "M001", "회원가입 되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "M002", "로그인 되었습니다."),
    REISSUE_SUCCESS(HttpStatus.OK, "M003", "재발급 되었습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "M004", "로그아웃 되었습니다."),
    GET_MY_INFO_SUCCESS(HttpStatus.OK, "M005", "내 정보 조회 완료"),

    UPDATE_SUCCESS(HttpStatus.OK, "M006", "업데이트 완료"),
    DELETE_SUCCESS(HttpStatus.OK, "M007", "삭제 완료");


    private HttpStatus status;
    private final String code;
    private final String message;
}
