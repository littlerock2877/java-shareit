package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;
import java.util.List;

public interface ItemRepository {
    Item addItem(Item item);

    Item getItemById(Long itemId);

    List<Item> getOwnedItems(Long userId);

    List<Item> searchItemsByQuery(String query);

    Item updateItem(Item item);
}
