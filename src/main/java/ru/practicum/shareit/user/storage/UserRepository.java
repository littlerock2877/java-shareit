package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.User;
import java.util.List;

public interface UserRepository {
    User createUser(User userUpdateDto);

    User updateUser(User userDto);

    User getUserById(Long userId);

    List<User> getAllUsers();

    void deleteUser(Long userId);
}
