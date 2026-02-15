package com.bino.mcp.adapter.in.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Centralized exception handling (best practice: @ControllerAdvice).
 * Uses RFC 7807 ProblemDetail (Spring 6 / Boot 3).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation error(s)"
        );
        problemDetail.setType(URI.create("https://api.bino.com/problems/validation-error"));
        problemDetail.setTitle("Validation Failed");
        problemDetail.setProperty("errors", errors);

        log.warn("Validation error: {}", errors);
        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        problemDetail.setType(URI.create("https://api.bino.com/problems/illegal-argument"));
        problemDetail.setTitle("Invalid Argument");

        log.warn("Illegal argument: {}", ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An internal error occurred"
        );
        problemDetail.setType(URI.create("https://api.bino.com/problems/internal-error"));
        problemDetail.setTitle("Internal Server Error");

        log.error("Unexpected error", ex);
        return problemDetail;
    }
}
