package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * A DTO for the {@link ru.clevertec.ecl.entities.Tag} entity
 */
@Data
@Builder
@ToString
public class TagDto {

    private long id;
    private String name;
}
