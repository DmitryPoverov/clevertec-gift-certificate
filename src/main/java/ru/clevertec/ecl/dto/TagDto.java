package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Value;

/**
 * A DTO for the {@link ru.clevertec.ecl.entities.Tag} entity
 */
@Value
@Builder
public class TagDto {

    long id;
    String name;
}
