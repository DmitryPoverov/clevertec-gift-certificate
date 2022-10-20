package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.ecl.dto.OrderCostAndDateDto;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.entities.Order;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        imports = LocalDateTime.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "createDate", expression = "java(LocalDateTime.now())")
    Order dtoToOrder (OrderDto dto);

    OrderDto orderToDto(Order order);

    OrderCostAndDateDto orderToCostAndDateDto(Order order);
}
