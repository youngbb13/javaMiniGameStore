package com.example.gamestore.exception;

import com.example.gamestore.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// ловить винятки з усіх @RestController
@RestControllerAdvice
public class GlobalExceptionHandler {

    // якщо хтось кинув InvalidAmountException — цей метод відповість
    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAmount(InvalidAmountException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughMoney(NotEnoughMoneyException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage(), 400);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(GameAlreadyOwnedException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyOwned(GameAlreadyOwnedException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage(), 400);
        return ResponseEntity.badRequest().body(body);
    }
}