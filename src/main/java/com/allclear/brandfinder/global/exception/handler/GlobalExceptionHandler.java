package com.allclear.brandfinder.global.exception.handler;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.allclear.brandfinder.global.exception.CustomException;
import com.allclear.brandfinder.global.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ErrorResponse> handlerCustomException(CustomException e) {
        log.error("CustomException : {}", e.getMessage());

        ErrorResponse errorResponse = ErrorResponse.create()
                .httpStatus(e.getErrorCode().getStatus())
                .message(e.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException(CustomException e) {
        log.error("MethodArgumentNotValidException : {}", e.getMessage());

        ErrorResponse errorResponse = ErrorResponse.create()
                .httpStatus(e.getErrorCode().getStatus())
                .message(e.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSqlException(SQLIntegrityConstraintViolationException e) {
        log.error("SQLIntegrityConstraintViolationException : {}", e.getMessage());

        ErrorResponse response = ErrorResponse.create()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException : {}", e.getMessage());

        ErrorResponse response = ErrorResponse.create()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(e.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e) {
        log.error("NullPointerException : {}", e.getMessage());

        ErrorResponse response = ErrorResponse.create()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(e.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        log.error("MissingRequestHeaderException : {}", e.getMessage());

        ErrorResponse response = ErrorResponse.create()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(e.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

}
