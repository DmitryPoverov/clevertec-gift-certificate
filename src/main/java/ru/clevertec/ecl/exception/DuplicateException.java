package ru.clevertec.ecl.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DuplicateException extends CustomException {

    private static final HttpStatus HTTP_STATUS_TO_SHOW_IT_USER = HttpStatus.NOT_ACCEPTABLE;

    public DuplicateException(String dto, String field, Object value) {
        super(String.format("%s with %s = %s already exists.", dto, field, value),
                HTTP_STATUS_TO_SHOW_IT_USER,
                HTTP_STATUS_TO_SHOW_IT_USER.value() + "01");
    }
}
