package com.shoppingmall.dto;

public class CartResponseDto {
    private String message;
    private String user_id;

    // Constructors, getters and setters

    public CartResponseDto() {}

    public CartResponseDto(String message, String user_id) {
        this.message = message;
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }
}

