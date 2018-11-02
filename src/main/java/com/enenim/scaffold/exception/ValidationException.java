package com.enenim.scaffold.exception;

import com.enenim.scaffold.shared.Validation;
import com.enenim.scaffold.util.message.ExceptionMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ValidationException extends RuntimeException{
    private Validation validation;

    public ValidationException(Validation validation) {
        super(ExceptionMessage.msg("validation_failure"));
        setValidation(validation);
    }
}