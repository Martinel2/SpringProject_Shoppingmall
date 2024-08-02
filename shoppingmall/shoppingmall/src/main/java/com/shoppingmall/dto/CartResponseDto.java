package com.shoppingmall.dto;

public class CartResponseDto {
    private String message;
    private String userId;

    // Constructors, getters and setters
    public CartResponseDto(String message, String userId) {
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

