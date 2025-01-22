package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    private Map<Long, Item> items = new HashMap<>();
    private long counter = 0;

    @Override
    public Item addItem(Item item) {
        long id = generateId();
        item.setId(id);
        items.put(id, item);
        return item;
    }

    @Override
    public Item getItemById(Long itemId) {
        Item item = items.get(itemId);
        if (item == null) {
            throw new NotFoundException(String.format("Item with id %d doesn't exist", itemId));
        }
        return item;
    }

    @Override
    public List<Item> getOwnedItems(Long userId) {
        return items.entrySet().stream()
                .filter(item -> item.getValue().getOwner().getId().equals(userId))
                .map(item -> item.getValue())
                .toList();
    }

    @Override
    public List<Item> searchItemsByQuery(String query) {
        return items.entrySet().stream()
                .filter(item -> item.getValue().getDescription().toLowerCase().contains(query.toLowerCase()) ||
                        item.getValue().getName().toLowerCase().contains(query.toLowerCase()))
                .filter(item -> item.getValue().getAvailable())
                .map(item -> item.getValue())
                .toList();
    }

    @Override
    public Item updateItem(Item item) {
        Item existingItem = getItemById(item.getId());
        if (item.getName() != null) {
            existingItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            existingItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            existingItem.setAvailable(item.getAvailable());
        }
        return existingItem;
    }

    private long generateId() {
        return counter++;
    }
}
