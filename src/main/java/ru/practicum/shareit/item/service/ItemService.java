package ru.practicum.shareit.item.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithComments;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemService {
    @Transactional
    ItemDto addItem(ItemDto itemDto, Long userid);

    @Transactional
    ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId);

    ItemDtoWithComments getItem(Long itemId);

    List<ItemDto> getOwnedItems(Long userId);

    List<ItemDto> searchItemsByQuery(String query);

    CommentDto createComment(Long userId, Long itemId, CommentDto commentDto);
}
