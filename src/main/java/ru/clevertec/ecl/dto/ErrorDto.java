package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorDto {

    int httpStatus;
    ErrorMessage message;

}
