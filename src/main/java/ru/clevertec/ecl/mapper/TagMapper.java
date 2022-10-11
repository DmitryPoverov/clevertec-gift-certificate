package ru.clevertec.ecl.mapper;

import org.mapstruct.*;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entities.Tag;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface TagMapper {

    TagDto tagToDto(Tag tag);

    @Mapping(target = "id", ignore = true)
    Tag dtoToTag(TagDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(@MappingTarget Tag tag, TagDto dto);
}
