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

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAllOrders(@PageableDefault Pageable pageable) {
        List<OrderDto> allOrders = orderService.findAllOrders(pageable);
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> findOrderByOrderId(@PathVariable long orderId) {
        OrderDto orderById = orderService.findOrderById(orderId);
        return ResponseEntity.ok(orderById);
    }


    @GetMapping("/users")
    public ResponseEntity<OrderCostAndDateDto> findOrderByUserIdAndOrderId(@RequestParam long userId,
                                                                           @RequestParam long orderId) {
        OrderCostAndDateDto costAndDateOfOrder = orderService.findOrderCostAndDateByUserIdAndOrderId(userId, orderId);
        return ResponseEntity.ok(costAndDateOfOrder);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderCostAndDateDto>> findAllOrdersByUserId(@PathVariable long userId,
                                                                           @PageableDefault Pageable pageable) {
        List<OrderCostAndDateDto> orders = orderService.findAllOrdersByUserId(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/users/nick-name/{nickName}")
    public ResponseEntity<List<OrderCostAndDateDto>> findAllOrdersByUserName(@PathVariable String nickName,
                                                                             @PageableDefault Pageable pageable) {
        List<OrderCostAndDateDto> orders = orderService.findAllOrdersByUserNickName(nickName, pageable);
        return ResponseEntity.ok(orders);
    }


    @PostMapping
    public ResponseEntity<OrderDto> createNewOrder(@RequestParam long userId,
                                                   @RequestParam long certificateId) {
        OrderDto newOrder = orderService.createNewOrder(userId, certificateId);
        return ResponseEntity.ok(newOrder);
    }

    @PostMapping("/dtos")
    public ResponseEntity<OrderDto> createNewOrderFromDto(@RequestBody OrderDto dto) {
        OrderDto newOrder = orderService.createNewOrder(dto);
        return ResponseEntity.ok(newOrder);
    }
}
