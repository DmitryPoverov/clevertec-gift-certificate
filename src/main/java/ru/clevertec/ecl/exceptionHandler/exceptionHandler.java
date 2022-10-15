package ru.clevertec.ecl.exceptionHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.ecl.dto.ErrorMessage;
import ru.clevertec.ecl.dto.ErrorDto;
import ru.clevertec.ecl.exception.CustomException;
import ru.clevertec.ecl.exception.DuplicateException;
import ru.clevertec.ecl.exception.NotFountException;

@Slf4j
@RestControllerAdvice
public class exceptionHandler {

    @ExceptionHandler(NotFountException.class)
    public ResponseEntity<?> handleNotFountException(CustomException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> handleDuplicateException(CustomException e) {
        return getResponseEntity(e);
    }

    private ResponseEntity<ErrorDto> getResponseEntity(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorDto.builder()
                        .httpStatus(e.getHttpStatus().value())
                        .message(ErrorMessage.builder()
                                .errorMessage(e.getMessage())
                                .errorCode(e.getErrorCode())
                                .build())
                        .build());
    }

}
