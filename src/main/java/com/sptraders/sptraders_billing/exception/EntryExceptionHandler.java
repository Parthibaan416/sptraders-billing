package com.sptraders.sptraders_billing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntryExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(EntryException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(String.valueOf(HttpStatus.NOT_FOUND.value()));
        error.setMessage(e.getMessage());
        error.setTime(String.valueOf(System.currentTimeMillis()));

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }
}
