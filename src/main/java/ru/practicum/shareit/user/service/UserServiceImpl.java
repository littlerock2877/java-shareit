package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
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
        User user = userMapper.toModel(userDto);
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        UserDto existingUser = getUserById(userId);
        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) {
            existingUser.setName(userDto.getName());
        }
        User user = userMapper.toModel(existingUser);
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto deleteUser(Long userId) {
        UserDto userDto = getUserById(userId);
        userRepository.deleteById(userId);
        return userDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d doesn't exist", userId)));
        return userMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }
}
