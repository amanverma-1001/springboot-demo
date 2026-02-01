package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<APIError> handleUserNotFound(UserNotFoundException ex){
        APIError error = new APIError(HttpStatus.NOT_FOUND.value() , ex.getMessage() );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);

    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<APIError> handleUserAlreadyExists(UserAlreadyExistsException ex){
        APIError error = new APIError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> handleValidationError(MethodArgumentNotValidException ex){
         APIError error = new APIError(HttpStatus.BAD_REQUEST.value(), ex.getBindingResult()
                 .getFieldErrors()
                 .stream()
                 .findFirst()
                 .map(FieldError::getDefaultMessage)
                 .orElse("Validation failed"));

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
