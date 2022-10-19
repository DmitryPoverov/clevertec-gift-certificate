package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.entities.User;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        imports = LocalDateTime.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserDto userToDto(User user);

    User dtoToUser(UserDto dto);
}
