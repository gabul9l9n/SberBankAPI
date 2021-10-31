package org.example.exception;

import org.example.exception.account.AccountNotFoundException;
import org.example.exception.api.ApiException;
import org.example.exception.card.CardNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    private final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    @ExceptionHandler(value = {CardNotFoundException.class})
    public ResponseEntity<Object> handleCardNotFoundException(CardNotFoundException e) {
        ApiException apiException = new ApiException(ZonedDateTime.now(), NOT_FOUND, e.getMessage());

        return new ResponseEntity<>(apiException, NOT_FOUND);
    }

    @ExceptionHandler(value = {AccountNotFoundException.class})
    public ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException e) {
        ApiException apiException = new ApiException(ZonedDateTime.now(), NOT_FOUND, e.getMessage());

        return new ResponseEntity<>(apiException, NOT_FOUND);
    }
}
