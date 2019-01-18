package com.enenim.scaffold.controller;

import com.enenim.scaffold.dto.response.ExceptionResponse;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.service.MailSenderService;
import com.enenim.scaffold.shared.ErrorValidator;
import com.enenim.scaffold.util.message.ExceptionMessage;
import com.enenim.scaffold.util.message.ValidationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(ScaffoldException.class)
    public ResponseEntity<ExceptionResponse> scaffoldException(ScaffoldException ex) {
        ExceptionResponse response = new ExceptionResponse();
        if(ex.getStatus() == HttpStatus.BAD_REQUEST){
            response.setErrors(ex.getErrors());
        }else {
            mailTypeResolverService.send(ex);
        }
        response.setErrorCode(ex.getStatus().toString());
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> validationError(MethodArgumentNotValidException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        response.setErrorMessage(ValidationMessage.msg("validation_failure"));
        response.setErrors(ErrorValidator.errors(ex));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> methodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.METHOD_NOT_ALLOWED.toString());
        response.setErrorMessage(ex.getMessage());
        response.setErrors(null);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> httpMediaType(HttpMediaTypeNotSupportedException ex) {
        mailTypeResolverService.send(ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString());
        response.setErrorMessage(ExceptionMessage.msg("unsupported_media_type"));
        response.setErrors(null);
        return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> messageNotReadableException(HttpMessageNotReadableException ex) {
        mailTypeResolverService.send(ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.NO_CONTENT.toString());
        response.setErrorMessage(ExceptionMessage.msg("missing_request_content"));
        response.setErrors(null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
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

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> unAuthorized(UnAuthorizedException ex) {
        mailTypeResolverService.send(ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.UNAUTHORIZED.toString());
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
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