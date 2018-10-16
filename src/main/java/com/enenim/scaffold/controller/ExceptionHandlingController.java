package com.enenim.scaffold.controller;

import com.enenim.scaffold.dto.response.ExceptionResponse;
import com.enenim.scaffold.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionHandlingController {
    private final MailSenderService mailTypeResolverService;

    @Autowired
    public ExceptionHandlingController(MailSenderService mailTypeResolverService) {
        this.mailTypeResolverService = mailTypeResolverService;
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> nullPointer(NullPointerException ex) {
        String msgPrefix = "Null pointer exception occur, details => ";
        mailTypeResolverService.send(ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        response.setMessage(ex, msgPrefix);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exception(Exception ex) {
        String msgPrefix = "Exception occur, details => ";
        mailTypeResolverService.send(ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        response.setMessage(ex, msgPrefix);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}