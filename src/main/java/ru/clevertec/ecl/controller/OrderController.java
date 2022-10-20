package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.OrderCostAndDateDto;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.service.interfaces.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable long id) {
        OrderDto orderById = orderService.findOrderById(id);
        return ResponseEntity.ok(orderById);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAllOrders(@PageableDefault Pageable pageable) {
        List<OrderDto> allOrders = orderService.findAllOrders(pageable);
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/users")
    public ResponseEntity<OrderCostAndDateDto> findOrderByOrderIdAndUserId(@RequestParam long userId,
                                                                           @RequestParam long orderId) {
        OrderCostAndDateDto costAndDateOfOrder = orderService.findOrderCostAndDateByUserIdAndOrderId(userId, orderId);
        return ResponseEntity.ok(costAndDateOfOrder);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createNewOrder(@RequestParam long userId,
                                                   @RequestParam long certificateId) {
        OrderDto newOrder = orderService.createNewOrder(userId, certificateId);
        return ResponseEntity.ok(newOrder);
    }

    @PostMapping("/dtos")
    public ResponseEntity<OrderDto> createNewOrderDto(@RequestBody OrderDto dto) {
        OrderDto newOrder = orderService.createNewOrder(dto);
        return ResponseEntity.ok(newOrder);
    }
}
