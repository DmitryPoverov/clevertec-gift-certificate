package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {

    private String errorMessage;
    private String errorCode;
}
