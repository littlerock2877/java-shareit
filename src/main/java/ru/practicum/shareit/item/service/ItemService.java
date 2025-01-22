package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import java.util.List;

public interface ItemService {
    ItemDto addItem(ItemDto itemDto, Long userid);

    ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId);

    ItemDto getItem(Long itemId);

    List<ItemDto> getOwnedItems(Long userId);

    List<ItemDto> searchItemsByQuery(String query);
}
