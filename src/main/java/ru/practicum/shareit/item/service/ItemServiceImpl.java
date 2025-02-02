package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotEnoughRightsException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemDto addItem(ItemDto itemDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d doesn't exist", userId)));
        Item item = itemMapper.toModel(itemDto, user);
        return itemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d doesn't exist", userId)));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(String.format("Item with id %d doesn't exist", itemId)));
        if (!item.getOwner().equals(user)) {
            throw new NotEnoughRightsException(String.format("User with id %d can't update item wuth id %d", userId, itemId));
        }
        itemDto.setId(itemId);
        item = itemMapper.toModel(itemDto, user);
        return itemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(String.format("Item with id %d doesn't exist", itemId)));
        return itemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getOwnedItems(Long userId) {
        return itemRepository.findAllByOwnerId(userId).stream().map(itemMapper::toItemDto).toList();
    }

    @Override
    public List<ItemDto> searchItemsByQuery(String query) {
        if (query.isEmpty()) {
            return List.of();
        }
        return itemRepository.searchByQuery(query)
                .stream()
                .map(itemMapper::toItemDto)
                .toList();
    }
}
