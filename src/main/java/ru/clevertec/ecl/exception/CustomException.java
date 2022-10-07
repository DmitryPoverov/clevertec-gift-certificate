package ru.clevertec.ecl.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;

    public CustomException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.httpStatus = status;
        this.errorCode = errorCode;

    }
}