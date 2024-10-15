package com.example.demo.formbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors.
     *
     * @param ex The MethodArgumentNotValidException exception.
     * @return A ResponseEntity with error details and HTTP status 400.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (existing, replacement) -> existing // If duplicate keys, keep existing
                ));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions.
     *
     * @param ex The Exception.
     * @return A ResponseEntity with error details and HTTP status 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "An unexpected error occurred.");
        // Optionally, add ex.getMessage() for debugging (not recommended in production)
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
