package com.cpt.study.exception;

import com.cpt.study.common.BaseException;
import com.cpt.study.common.ErrorCode;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(message, ErrorCode.MEMBER_NOT_FOUND);
    }
}
