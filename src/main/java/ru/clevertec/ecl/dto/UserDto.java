package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Value;
import ru.clevertec.ecl.entities.Order;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class UserDto {

    Long id;
    String nickName;
    String userName;
    List<Order> orderList = new ArrayList<>();
}
