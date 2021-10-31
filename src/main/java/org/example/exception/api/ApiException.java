package org.example.exception.api;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ApiException {
    private final ZonedDateTime timestamp;
    private final HttpStatus httpStatus;
    private final String message;

    public ApiException(ZonedDateTime timestamp, HttpStatus httpStatus, String message) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
