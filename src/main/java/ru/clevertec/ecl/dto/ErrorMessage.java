package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorMessage {

    String errorMessage;
    String errorCode;
}
