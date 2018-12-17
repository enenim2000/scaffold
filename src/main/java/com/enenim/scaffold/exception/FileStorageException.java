package com.enenim.scaffold.exception;

import com.enenim.scaffold.util.message.ExceptionMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class FileStorageException extends RuntimeException {

    private HttpStatus status;

    public FileStorageException(String key) {
        super(ExceptionMessage.msg(key));
        status = HttpStatus.EXPECTATION_FAILED;
    }

    public FileStorageException(String key, String role) {
        super(ExceptionMessage.msg(key, role));
        status = HttpStatus.EXPECTATION_FAILED;
    }
}