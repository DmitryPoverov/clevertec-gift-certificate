package ru.clevertec.ecl.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DuplicateException extends CustomException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_ACCEPTABLE;

    public DuplicateException(String dto, String field, Object value) {
        super(String.format("%s with %s = %s already exists.", dto, field, value),
                HTTP_STATUS,
                HTTP_STATUS.value() + "01");
    }
}
