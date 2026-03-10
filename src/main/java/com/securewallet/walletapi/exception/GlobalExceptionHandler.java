package com.securewallet.walletapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<StandardError> handleResponseStatusException(ResponseStatusException ex) {

        StandardError errorResponse = new StandardError(
                LocalDateTime.now().toString(),
                ex.getStatusCode().value(),
                ex.getReason()
        );

        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    public record StandardError(String timestamp, Integer status, String message) {
    }
}