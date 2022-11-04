package ru.clevertec.ecl.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.OrderCostAndDateDto;
import ru.clevertec.ecl.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto findOrderById(long id);

    List<OrderDto> findAllOrders(Pageable pageable);

    OrderCostAndDateDto findOrderCostAndDateByUserIdAndOrderId(long userId, long orderId);

    OrderDto createNewOrder(long userId, long certificateId);

    OrderDto createNewOrder(OrderDto dto);

    List<OrderCostAndDateDto> findAllOrdersByUserNickName(String name, Pageable pageable);

    List<OrderCostAndDateDto> findAllOrdersByUserId(long userId, Pageable pageable);
}
