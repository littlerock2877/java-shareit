package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getById_shouldReturnUserNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(1L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getById_shouldReturnUser() {
        User user = new User(1L, "Alex", "alex.b@yandex.ru");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.toUserDto(any())).thenReturn(new UserDto(user.getId(), user.getName(), user.getEmail()));

        UserDto expected = new UserDto(1L, "Alex", "alex.b@yandex.ru");
        UserDto actual = userService.getUserById(user.getId());

        assertEquals(expected, actual);
    }

    @Test
    void update_shouldReturnUserNotFoundException() {
        UserDto dto = new UserDto(999L, "Alex", "alex.b@yandex.ru");
        when(userRepository.findById(dto.getId())).thenReturn(Optional.empty());
        Long userId = 999L;

        assertThatThrownBy(() -> userService.updateUser(dto, userId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_shouldUpdateName() {
        UserDto dto = new UserDto(null, "Alex", "john.d@yandex.ru");
        User user = new User(1L, "Alex", "alex.b@yandex.ru");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserDto(any())).thenReturn(new UserDto(user.getId(), user.getName(), user.getEmail()));

        UserDto expected = new UserDto(1L, "Alex", "john.d@yandex.ru");
        UserDto actual = userService.updateUser(dto, 1L);

        assertEquals(expected, actual);
    }

    @Test
    void getAll_shouldReturnListNotEmpty() {
        User user1 = new User(1L, "Alex", "alex.b@yandex.ru");
        User user2 = new User(2L, "Bill", "bill.d@yandex.ru");
        User user3 = new User(3L, "John", "john.d@yandex.ru");
        List<User> page = List.of(user1, user2, user3);

        when(userRepository.findAll()).thenReturn(page);
        when(userMapper.toUserDto(any())).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            return new UserDto(user.getId(), user.getName(), user.getEmail());
        });

        List<UserDto> expectedList = Stream.of(user1, user2, user3).map(user -> userMapper.toUserDto(user)).toList();
        List<UserDto> actualList = userService.getAllUsers();


        assertEquals(expectedList, actualList);
    }
}
