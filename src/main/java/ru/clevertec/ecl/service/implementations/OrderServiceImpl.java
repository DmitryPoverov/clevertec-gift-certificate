package ru.clevertec.ecl.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.OrderCostAndDateDto;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.entities.GiftCertificate;
import ru.clevertec.ecl.entities.Order;
import ru.clevertec.ecl.entities.User;
import ru.clevertec.ecl.exception.NotFountException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.service.interfaces.GiftCertificateService;
import ru.clevertec.ecl.service.interfaces.OrderService;
import ru.clevertec.ecl.service.interfaces.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final GiftCertificateService certificateService;

    @Override
    public OrderDto findOrderById(long id) {
        return orderRepository.findById(id)
                .map(orderMapper::orderToDto)
                .orElseThrow(() -> new NotFountException("order", "id", id));
    }

    @Override
    public List<OrderDto> findAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::orderToDto)
                .getContent();
    }

    @Override
    public OrderDto createNewOrder(long userId, long certificateId) {
        UserDto userById = userService.findUserById(userId);
        GiftCertificateDto dtoById = certificateService.findCertificateById(certificateId);

        GiftCertificate certificateToSave = certificateService.findByNameOrSave(dtoById);
        User userToSave = userService.findUserByNickNameOrSave(userById);

        Order newOrder = Order.builder()
                .price(certificateToSave.getPrice())
                .user(userToSave)
                .certificate(certificateToSave)
                .createDate(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(newOrder);
        return orderMapper.orderToDto(savedOrder);
    }

    @Override
    public OrderDto createNewOrder(OrderDto dto) {
        Order order = orderMapper.dtoToOrder(dto);
        order.setPrice(dto.getCertificate().getPrice());
        Order savedOrder = orderRepository.save(order);
        return orderMapper.orderToDto(savedOrder);
    }

    @Override
    public OrderCostAndDateDto findOrderCostAndDateByUserIdAndOrderId(long userId, long orderId) {
        userService.findUserById(userId);
        findOrderById(orderId);

        return orderRepository.findOrderByUserIdAndByOrderId(userId, orderId)
                .map(orderMapper::orderToCostAndDateDto)
                .orElseThrow(() -> new NotFountException("order", "id", orderId));
    }

    @Override
    public List<OrderCostAndDateDto> findAllOrdersByUserNickName(String name, Pageable pageable) {
        return orderRepository.findAllOrdersByUserNickName(name, pageable)
                .stream()
                .map(orderMapper::orderToCostAndDateDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderCostAndDateDto> findAllOrdersByUserId(long userId, Pageable pageable) {
        return orderRepository.findAllOrdersByUserId(userId, pageable)
                .stream()
                .map(orderMapper::orderToCostAndDateDto)
                .collect(Collectors.toList());
    }
}
