package ru.clevertec.ecl.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.entities.User;
import ru.clevertec.ecl.exception.NotFountException;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.service.interfaces.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToDto)
                .getContent();
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFountException("user", "id", id));
        return userMapper.userToDto(user);
    }

    @Override
    public UserDto findUserByNickName(String nickName) {
        return userRepository.findUserByNickName(nickName)
                .map(userMapper::userToDto)
                .orElseThrow(() -> new NotFountException("user", "name", nickName));
    }
}
