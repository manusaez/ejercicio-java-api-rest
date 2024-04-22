package com.nisum.apirest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nisum.apirest.dto.ErrorDto;
import com.nisum.apirest.exception.BusinessException;
import com.nisum.apirest.exception.UserNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(UserNotFoundException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.OK);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(HttpMessageNotReadableException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(IllegalArgumentException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(BusinessException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.OK);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(HttpRequestMethodNotSupportedException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
}
