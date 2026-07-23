package com.ltd.rpg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/** Converts expected API errors into concise, browser-readable JSON. */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException exception
    ) {
        HttpStatus status = exception.getMessage() != null
                && exception.getMessage().startsWith("Game session not found:")
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(
                new ApiError(status.value(), status.getReasonPhrase(), exception.getMessage(), Instant.now())
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleIllegalState(
            IllegalStateException exception
    ) {
        HttpStatus status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(
                new ApiError(status.value(), status.getReasonPhrase(), exception.getMessage(), Instant.now())
        );
    }

    public record ApiError(
            int status,
            String error,
            String message,
            Instant timestamp
    ) {
    }
}
