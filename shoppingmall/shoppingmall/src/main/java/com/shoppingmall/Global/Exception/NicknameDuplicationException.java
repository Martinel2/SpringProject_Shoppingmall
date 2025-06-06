package com.shoppingmall.Global.Exception;

import com.shoppingmall.Global.Response.Error.ErrorCode;

public class NicknameDuplicationException extends RuntimeException{
    private final ErrorCode errorCode;

    public NicknameDuplicationException() {
        super(ErrorCode.NICKNAME_DUPLICATION.getMessage());
        this.errorCode = ErrorCode.EMAIL_DUPLICATION;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
