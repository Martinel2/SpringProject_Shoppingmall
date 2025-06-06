package com.shoppingmall.Global.Exception;

import com.shoppingmall.Global.Response.Error.ErrorCode;
import lombok.Getter;

@Getter
public class PostNoPermissionException extends RuntimeException {
    private final ErrorCode errorCode;


    public PostNoPermissionException() {
        super(ErrorCode.POST_NO_PERMISSION.getMessage());
        this.errorCode = ErrorCode.POST_NO_PERMISSION;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
} 