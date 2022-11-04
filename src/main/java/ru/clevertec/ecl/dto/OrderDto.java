package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Value;
import ru.clevertec.ecl.entities.GiftCertificate;
import ru.clevertec.ecl.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class OrderDto {

    Long id;
    BigDecimal price;
    LocalDateTime createDate;
    User user;
    GiftCertificate certificate;
}
