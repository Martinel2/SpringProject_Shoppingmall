package com.shoppingmall.Global.Exception;

import com.shoppingmall.Global.Response.Error.ErrorCode;

public class InvalidTokenException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN.getMessage());
        this.errorCode = ErrorCode.INVALID_TOKEN;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
