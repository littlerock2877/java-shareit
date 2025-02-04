package ru.practicum.shareit.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.UserDto;
import java.util.List;

@Transactional(readOnly = true)
public interface UserService {
    @Transactional
    UserDto createUser(UserDto userDto);

    @Transactional
    UserDto updateUser(UserDto userDto, Long userId);

    @Transactional
    UserDto deleteUser(Long userId);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();
}
