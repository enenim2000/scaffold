package com.enenim.scaffold.exception;

import com.enenim.scaffold.util.message.ExceptionMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class ScaffoldException extends RuntimeException{

    private HttpStatus status;

    private Object errors;

    public ScaffoldException(String key) {
        super(ExceptionMessage.msg(key));
        status = HttpStatus.EXPECTATION_FAILED;
    }

    public ScaffoldException(String key, HttpStatus status) {
        super(ExceptionMessage.msg(key));
        this.status = status;
    }

    public ScaffoldException(String key, String role) {
        super(ExceptionMessage.msg(key, role));
        status = HttpStatus.EXPECTATION_FAILED;
    }

    public ScaffoldException(String key, String role, HttpStatus status) {
        super(ExceptionMessage.msg(key, role));
        this.status = status;
    }

    public ScaffoldException(String key, HttpStatus status, Object errors) {
        super(ExceptionMessage.msg(key));
        this.status = status;
        this.errors = errors;
    }

    public ScaffoldException(Object message) {
        super((String) message);
        status = HttpStatus.EXPECTATION_FAILED;
    }
}