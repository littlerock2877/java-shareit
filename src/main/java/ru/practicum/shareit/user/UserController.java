package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        log.info("Creating user {} - Started", userDto);
        userDto = userService.createUser(userDto);
        log.info("Creating user {} - Finished", userDto);
        return userDto;
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        log.info("Updating user {} - Started", userDto);
        userDto = userService.updateUser(userDto, userId);
        log.info("Updating user {} - Finished", userDto);
        return userDto;
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        log.info("Getting user with id {} - Started", userId);
        UserDto userDto = userService.getUserById(userId);
        log.info("Getting user with id {} - User = {}", userId, userDto);
        return userDto;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("Getting all users - Started");
        List<UserDto> allUsers = userService.getAllUsers();
        log.info("Getting all users - {}", allUsers.toString());
        return allUsers;
    }

    @DeleteMapping("/{userId}")
    public UserDto deleteUser(@PathVariable Long userId) {
        log.info("Deleting user with id {} - Started", userId);
        UserDto userDto = userService.deleteUser(userId);
        log.info("Deleting user with id {} - Finished", userId);
        return userDto;
    }
}
