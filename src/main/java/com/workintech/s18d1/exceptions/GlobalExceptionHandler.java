package com.workintech.s18d1.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BurgerException.class)
    public ResponseEntity<BurgerErrorResponse> handleBurgerException(
            BurgerException exception) {

        log.warn("Burger API error: {}", exception.getMessage());

        return ResponseEntity
            .status(exception.getHttpStatus())
            .body(new BurgerErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BurgerErrorResponse> handleException(
            Exception exception) {

        log.error("Unexpected Burger API error", exception);

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new BurgerErrorResponse(exception.getMessage()));
    }
}
