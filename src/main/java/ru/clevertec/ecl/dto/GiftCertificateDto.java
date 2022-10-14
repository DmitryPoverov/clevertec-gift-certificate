package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A DTO for the {@link ru.clevertec.ecl.entities.GiftCertificate} entity
 */
@Value
@Builder
public class GiftCertificateDto {

    long id;
    String name;
    String description;
    BigDecimal price;
    long duration;
    LocalDateTime createDate;
    LocalDateTime lastUpdateDate;
    List<TagDto> tags;
}
