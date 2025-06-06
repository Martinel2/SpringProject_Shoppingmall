package com.shoppingmall.Global.Exception;

import com.shoppingmall.Global.Response.Error.ErrorCode;
import lombok.Getter;

@Getter
public class PostTitleBlankException extends RuntimeException {
    private final ErrorCode errorCode;


    public PostTitleBlankException() {
        super(ErrorCode.POST_TITLE_BLANK.getMessage());
        this.errorCode = ErrorCode.POST_TITLE_BLANK;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
} 