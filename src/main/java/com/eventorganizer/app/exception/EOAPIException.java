package com.eventorganizer.app.exception;

import org.springframework.http.HttpStatus;

public class EOAPIException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public EOAPIException(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public EOAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.httpStatus = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
