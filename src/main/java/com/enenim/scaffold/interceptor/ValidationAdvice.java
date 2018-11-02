package com.enenim.scaffold.interceptor;

import com.enenim.scaffold.exception.ValidationException;
import com.enenim.scaffold.shared.Validation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationAdvice.class);

    @Around("execution(* com.enenim.scaffold.dto.request.*.validateRequest(..))")
    public void interceptValidation(ProceedingJoinPoint joinPoint) throws Throwable {
        Validation validation = (Validation) joinPoint.proceed();
        LOGGER.info("Request {}", validation.errors());
        if(validation.hasErrors()) throw new ValidationException(validation);
    }
}
