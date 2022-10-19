package ru.clevertec.ecl.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.UserDto;

import java.util.List;

public interface UserService {


    List<UserDto> findAllUsers(Pageable pageable);

    UserDto findUserById(Long id);

    UserDto findUserByNickName(String nickName);
}
