package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.DuplicateException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private Map<Long, User> users = new HashMap<>();
    private long counter = 0;

    @Override
    public User createUser(User user) {
        long id = generateId();
        if (users.values().stream().anyMatch(existing -> existing.getEmail().equals(user.getEmail()))) {
            throw new DuplicateException(String.format("User with the same email %s already exists", user.getEmail()));
        }
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (users.values().stream().anyMatch(existing -> existing.getEmail().equals(user.getEmail()))) {
            throw new DuplicateException(String.format("User with the same email %s already exists", user.getEmail()));
        }
        User existingUser = users.get(user.getId());
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        return existingUser;
    }

    @Override
    public User getUserById(Long userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new NotFoundException(String.format("User with id %d doesn't exist", userId));
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return List.copyOf(users.values());
    }

    @Override
    public void deleteUser(Long userId) {
        users.remove(userId);
        counter--;
    }

    private long generateId() {
        return counter++;
    }
}
