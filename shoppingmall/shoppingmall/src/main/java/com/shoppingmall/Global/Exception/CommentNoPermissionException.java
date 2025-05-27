package com.shoppingmall.Global.Exception;


import com.shoppingmall.Global.Response.Error.ErrorCode;

public class CommentNoPermissionException extends RuntimeException {
    private final ErrorCode errorCode;

    public CommentNoPermissionException() {
        super(ErrorCode.COMMENT_NO_PERMISSION.getMessage());
        this.errorCode = ErrorCode.COMMENT_NO_PERMISSION;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
