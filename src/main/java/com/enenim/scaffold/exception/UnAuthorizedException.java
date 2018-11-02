package com.enenim.scaffold.exception;

import com.enenim.scaffold.util.message.ExceptionMessage;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String key) {
        super(ExceptionMessage.msg(key));
    }

    public UnAuthorizedException(String key, String role) {
        super(ExceptionMessage.msg(key, role));
    }

    public UnAuthorizedException(Exception e) {
        super(e.getMessage());
    }
}