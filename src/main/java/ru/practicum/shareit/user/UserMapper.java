package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;

@Component
public class UserMapper {
    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public User toModel(UserDto userDto, long userId) {
        return new User(
                userId,
                userDto.getName(),
                userDto.getEmail()
        );
    }
}
