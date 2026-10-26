package com.example.gamestore.exception;

import com.example.gamestore.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // ловить помилки валідації (@Valid) з усіх контролерів
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {

        // збираємо всі помилки полів в один рядок
        String message = e.getBindingResult()          // результат перевірки: що саме пішло не так
                .getFieldErrors()                      // список помилок, по одній на кожне некоректне поле
                .stream()                              // дозволяє пройтись по списку крок за кроком
                .map(error -> error.getField()         // назва поля, наприклад "price"
                        + ": "                         // розділювач
                        + error.getDefaultMessage())   // причина, наприклад "must not be null"
                .collect(Collectors.joining(", "));    // склеюємо всі рядки в один через кому

        // тіло відповіді в нашому єдиному форматі: текст помилки + число 400
        ErrorResponse body = new ErrorResponse(message, 400);

        // справжній HTTP-статус відповіді (має збігатися з числом у body)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}