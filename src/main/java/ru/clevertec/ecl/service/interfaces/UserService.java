package ru.clevertec.ecl.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.entities.User;

import java.util.List;

public interface UserService {

    List<UserDto> findAllUsers(Pageable pageable);

    UserDto findUserById(Long id);

    UserDto findUserByNickName(String nickName);

    User findUserByNickNameOrSave(UserDto dto);
}
