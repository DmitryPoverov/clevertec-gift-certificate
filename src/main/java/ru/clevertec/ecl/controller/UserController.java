package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.OrderCostAndDateDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.service.interfaces.OrderService;
import ru.clevertec.ecl.service.interfaces.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers(@PageableDefault Pageable pageable) {
        List<UserDto> allUsers = userService.findAllUsers(pageable);
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable long id) {
        UserDto userById = userService.findUserById(id);
        return ResponseEntity.ok(userById);
    }

    @GetMapping("/orders")
    public ResponseEntity<OrderCostAndDateDto> findOrdersOfUser(@RequestParam long userId,
                                                                @RequestParam long orderId) {
        OrderCostAndDateDto costAndDateOfOrder = orderService.findOrderCostAndDateByUserIdAndOrderId(userId, orderId);
        return ResponseEntity.ok(costAndDateOfOrder);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<OrderCostAndDateDto>> findAllOrdersByUserId(@PathVariable long userId,
                                                                           @PageableDefault Pageable pageable) {
        List<OrderCostAndDateDto> orders = orderService.findAllOrdersByUserId(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/nick-names/{nickName}")
    public ResponseEntity<UserDto> findUserByNickName(@PathVariable String nickName) {
        UserDto userByNickName = userService.findUserByNickName(nickName);
        return ResponseEntity.ok(userByNickName);
    }

    @GetMapping("/nick-names/orders/{nickName}")
    public ResponseEntity<List<OrderCostAndDateDto>> findAllOrdersByUserName(@PathVariable String nickName,
                                                                             @PageableDefault Pageable pageable) {
        List<OrderCostAndDateDto> orders = orderService.findAllOrdersByUserNickName(nickName, pageable);
        return ResponseEntity.ok(orders);
    }
}
