package com.shoppingmall.Global.Auth.Dto;

public class FindPwDto {
    private String message;
    private String email;

    public FindPwDto(String message, String email) {
        this.message = message;
        this.email = email;
    }

    // getters and setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

