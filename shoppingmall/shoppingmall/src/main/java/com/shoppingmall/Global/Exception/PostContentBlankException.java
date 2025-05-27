package com.shoppingmall.Global.Exception;

import com.shoppingmall.Global.Response.Error.ErrorCode;
import lombok.Getter;

@Getter
public class PostContentBlankException extends RuntimeException {
    private final ErrorCode errorCode;


    public PostContentBlankException() {
        super(ErrorCode.POST_CONTENT_BLANK.getMessage());
        this.errorCode = ErrorCode.POST_CONTENT_BLANK;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
} 