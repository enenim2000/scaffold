package com.enenim.scaffold.exception;

import com.enenim.scaffold.util.message.ExceptionMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScaffoldException extends RuntimeException{

    public ScaffoldException(String key) {
        super(ExceptionMessage.msg(key));
    }

    public ScaffoldException(String key, String role) {
        super(ExceptionMessage.msg(key, role));
    }
}