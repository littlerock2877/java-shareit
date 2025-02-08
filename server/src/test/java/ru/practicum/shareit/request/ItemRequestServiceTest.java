package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.service.ItemRequestServiceImpl;
import ru.practicum.shareit.request.storage.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ItemRequestServiceTest {
    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    @Mock
    private UserRepository userRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    ItemRequestMapper itemRequestMapper;

    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Test
    void create_shouldSaveItemRequest() {
        Long userId = 1L;
        User user = new User(userId, "Alex", "alex.b@yandex.ru");
        ItemRequestDto dtoShort = new ItemRequestDto(1L, "desc", LocalDateTime.now(), List.of());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(itemRequestRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        when(itemRequestMapper.toModel(any(), any())).thenAnswer(invocation -> {
            ItemRequestDto itemRequestDto = invocation.getArgument(0);
            User booker = invocation.getArgument(1);
            return new ItemRequest(itemRequestDto.getId(), itemRequestDto.getDescription(), booker, itemRequestDto.getCreated());
        });
        when(itemRequestMapper.toItemRequestDto(any(), any())).thenAnswer(invocation -> {
            ItemRequest itemRequest = invocation.getArgument(0);
            return new ItemRequestDto(itemRequest.getId(), itemRequest.getDescription(), itemRequest.getCreated(), List.of());
        });
        ItemRequestDto dto = itemRequestService.addRequest(userId, dtoShort);

        assertThat(dto.getDescription()).isEqualTo(dtoShort.getDescription());
    }

    @Test
    void create_shouldReturnUserNotFoundException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        ItemRequestDto dto = new ItemRequestDto(1L, "desc", LocalDateTime.now(), null);

        when(itemRequestMapper.toModel(any(), any())).thenAnswer(invocation -> {
            ItemRequestDto itemRequestDto = invocation.getArgument(0);
            User booker = invocation.getArgument(1);
            return new ItemRequest(itemRequestDto.getId(), itemRequestDto.getDescription(), booker, itemRequestDto.getCreated());
        });
        when(itemRequestMapper.toItemRequestDto(any(), any())).thenAnswer(invocation -> {
            ItemRequest itemRequest = invocation.getArgument(0);
            return new ItemRequestDto(itemRequest.getId(), itemRequest.getDescription(), itemRequest.getCreated(), List.of());
        });

        assertThrows(NotFoundException.class, () -> itemRequestService.addRequest(1L, dto));
        verify(itemRequestRepository, never()).save(any());
    }
}
