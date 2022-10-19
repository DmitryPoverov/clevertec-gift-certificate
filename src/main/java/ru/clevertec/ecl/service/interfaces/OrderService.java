package ru.clevertec.ecl.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto findOrderById(long id);

    List<OrderDto> findAllOrders(Pageable pageable);

    OrderDto createNewOrder(long userId, long certificateId);

    OrderDto createNewOrder(OrderDto dto);
}
