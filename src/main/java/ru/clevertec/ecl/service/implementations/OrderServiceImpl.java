package ru.clevertec.ecl.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.entities.GiftCertificate;
import ru.clevertec.ecl.entities.Order;
import ru.clevertec.ecl.entities.User;
import ru.clevertec.ecl.exception.NotFountException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.service.interfaces.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final GiftCertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

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
        GiftCertificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new NotFountException("gift certificate", "id", certificateId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFountException("user", "id", userId));

        Order newOrder = Order.builder()
                .price(certificate.getPrice())
                .user(user)
                .certificate(certificate)
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
}
