package com.allclear.brandfinder.global.response;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    public boolean success;
    public String message;
    public HttpStatus httpStatus;

    public ErrorResponse() {
        this.success = false;
    }

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.success = false;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    static public ErrorResponse create() {
        return new ErrorResponse();
    }

    public ErrorResponse message(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponse httpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

}
