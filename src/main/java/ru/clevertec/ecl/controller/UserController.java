package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.service.interfaces.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

    @GetMapping("/nicks/{nickName}")
    public ResponseEntity<UserDto> findUserByNickName (@PathVariable String nickName) {
        UserDto userByNickName = userService.findUserByNickName(nickName);
        return ResponseEntity.ok(userByNickName);
    }
}
