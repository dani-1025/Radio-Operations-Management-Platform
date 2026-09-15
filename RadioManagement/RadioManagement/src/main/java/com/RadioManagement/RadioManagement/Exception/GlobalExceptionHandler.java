package com.RadioManagement.RadioManagement.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        Map<String, Object> response = new HashMap<>();
        response.put("status", 400);
        response.put("message", "Validation failed");
        response.put("errors", errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExists(
            UserAlreadyExistsException exception) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", 400);
        response.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(ChannelNotFoundException.class)
    public ResponseEntity<?> handleChannelNotFound(
            ChannelNotFoundException exception) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", 404);
        response.put("message", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(DuplicateChannelException.class)
    public ResponseEntity<?> handleDuplicateChannel(
            DuplicateChannelException exception) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", 409);
        response.put("message", exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }
}