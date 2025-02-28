package com.url_shortener.exception;

import com.url_shortener.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomAliasExistsException.class})
    public ResponseEntity<ErrorResponse<String>> handleCustomAliasAlreadyExistsException(Exception e){
        ErrorResponse<String> errorResponse = new ErrorResponse.ErrorResponseBuilder<String>().setMessage(e.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
