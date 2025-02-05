package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.client.UserClient;
import ru.practicum.shareit.user.dto.UserDto;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDto userDto) {
        log.info("Creating user {} - Started", userDto);
        ResponseEntity<Object> user = userClient.createUser(userDto);
        log.info("Creating user {} - Finished", userDto);
        return user;
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        log.info("Updating user {} - Started", userDto);
        ResponseEntity<Object> user = userClient.updateUser(userDto, userId);
        log.info("Updating user {} - Finished", userDto);
        return user;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable Long userId) {
        log.info("Getting user with id {} - Started", userId);
        ResponseEntity<Object> user = userClient.getUser(userId);
        log.info("Getting user with id {} - User = {}", userId);
        return user;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        log.info("Getting all users - Started");
        ResponseEntity<Object> users = userClient.getUsers();
        log.info("Getting all users - Finished");
        return users;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        log.info("Deleting user with id {} - Started", userId);
        ResponseEntity<Object> user = userClient.deleteUser(userId);
        log.info("Deleting user with id {} - Finished", userId);
        return user;
    }
}
