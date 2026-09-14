package com.eventhub.api.exception;

import com.eventhub.api.dto.ApiErrorResponse;
import com.eventhub.api.dto.FieldErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException exception,
                                                                      HttpServletRequest request) {
        List<FieldErrorResponse> fieldErrors = exception.getFieldErrors()
                .stream()
                .map(e -> new FieldErrorResponse(e.getField(), e.getCode(), e.getDefaultMessage()))
                .toList();
        String path = request.getRequestURI();
        Instant timestamp = Instant.now();
        int status = HttpStatus.BAD_REQUEST.value();
        String code = "VALIDATION_ERROR";
        String message = "Request validation failed";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(timestamp, status, code, message, path, fieldErrors)
        );
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleOrderNotFoundException(OrderNotFoundException orderException, HttpServletRequest request) {


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.NOT_FOUND.value(),
                        orderException.getCode(),
                        orderException.getMessage(),
                        request.getRequestURI(), List.of()
                ));
    }
}
