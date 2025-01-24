package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.storage.UserRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toModel(userDto, -1);
        return userMapper.toUserDto(userRepository.createUser(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        getUserById(userId);
        User user = userMapper.toModel(userDto, userId);
        return userMapper.toUserDto(userRepository.updateUser(user));
    }

    @Override
    public UserDto deleteUser(Long userId) {
        UserDto userDto = getUserById(userId);
        userRepository.deleteUser(userId);
        return userDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        return userMapper.toUserDto(userRepository.getUserById(userId));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream().map(userMapper::toUserDto).toList();
    }
}
