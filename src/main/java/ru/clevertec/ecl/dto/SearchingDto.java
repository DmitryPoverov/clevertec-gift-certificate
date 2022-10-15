package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SearchingDto {

    String tagName;
    String partOfName;
    String partOfDescription;
    boolean sortByName;
    boolean sortAscending;
}
