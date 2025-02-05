package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.storage.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemRequestMapper itemRequestMapper;
    private final ItemMapper itemMapper;

    @Override
    public ItemRequestDto addRequest(Long userId, ItemRequestDto itemRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d doesn't exist", userId)));
        ItemRequest itemRequest = itemRequestMapper.toModel(itemRequestDto, user);
        return itemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest), null);
    }

    @Override
    public List<ItemRequestDto> getAllItemRequests(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d doesn't exist", userId)));
        return itemRequestRepository.getByRequesterIdOrderByCreatedAsc(userId)
                .stream()
                .map(itemRequest -> {
                    List<Item> items = itemRepository.getByRequestId(itemRequest.getId(), Sort.by("id").descending());
                    return itemRequestMapper.toItemRequestDto(itemRequest, items.stream().map(itemMapper::toItemDto).toList());
                })
                .toList();
    }

    @Override
    public ItemRequestDto getItemRequestById(Long userId, Long requestId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d doesn't exist", userId)));
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("ItemRequest with id %d doesn't exist", requestId)));
        List<Item> items = itemRepository.getByRequestId(itemRequest.getId(), Sort.by("id").descending());
        return itemRequestMapper.toItemRequestDto(itemRequest, items.stream().map(itemMapper::toItemDto).toList());
    }

    @Override
    public List<ItemRequestDto> getAllItemRequestsForUser(Long userId) {
        return itemRequestRepository.getByRequesterIdNot(userId)
                .stream()
                .map(itemRequest -> {
                    List<Item> items = itemRepository.getByRequestId(itemRequest.getId(), Sort.by("id").descending());
                    return itemRequestMapper.toItemRequestDto(itemRequest, items.stream().map(itemMapper::toItemDto).toList());
                })
                .toList();
    }
}
