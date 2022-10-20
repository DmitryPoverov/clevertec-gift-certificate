package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class OrderCostAndDateDto {

    BigDecimal price;
    LocalDateTime createDate;
}
