package ru.practicum.shareit.item.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.ItemDto;
import java.util.List;

@Transactional(readOnly = true)
public interface ItemService {
    @Transactional
    ItemDto addItem(ItemDto itemDto, Long userid);

    @Transactional
    ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId);

    ItemDto getItem(Long itemId);

    List<ItemDto> getOwnedItems(Long userId);

    List<ItemDto> searchItemsByQuery(String query);
}
