package ru.practicum.shareit.request.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ItemRequestMapper {
    public ItemRequestDto toItemRequestDto(ItemRequest itemRequest, List<ItemDto> items) {
        return new ItemRequestDto(
                itemRequest.getId(),
                itemRequest.getDescription(),
                itemRequest.getCreated(),
                items
        );
    }

    public ItemRequest toModel(ItemRequestDto itemRequestDto, User requester) {
        return new ItemRequest(
                itemRequestDto.getId(),
                itemRequestDto.getDescription(),
                requester,
                LocalDateTime.now()
        );
    }

    public ItemRequest toModel(ItemRequestDto itemRequestDto, User requester, LocalDateTime created) {
        return new ItemRequest(
                itemRequestDto.getId(),
                itemRequestDto.getDescription(),
                requester,
                created
        );
    }
}
