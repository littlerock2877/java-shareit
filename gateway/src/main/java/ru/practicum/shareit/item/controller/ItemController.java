package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.client.ItemClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestBody @Valid ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Adding item {} - Started", itemDto);
        ResponseEntity<Object> item = itemClient.addItem(itemDto, userId);
        log.info("Adding item {} - Finished", itemDto);
        return item;
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestBody ItemDto itemDto,
                              @RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId) {
        log.info("Updating item {} - Started", itemDto);
        ResponseEntity<Object> item = itemClient.updateItem(itemDto, itemId, userId);
        log.info("Updating item {} - Finished", itemDto);
        return item;
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable Long itemId) {
        log.info("Getting item with id {} - Started", itemId);
        ResponseEntity<Object> item = itemClient.getItem(itemId);
        log.info("Getting item with id {} - Finished", itemId);
        return item;
    }

    @GetMapping
    public ResponseEntity<Object> getOwnedItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Getting owned items for user with id {} - Started", userId);
        ResponseEntity<Object> ownedItems = itemClient.getOwnedItems(userId);
        log.info("Getting owned items for user with id {} - Finished", userId);
        return ownedItems;
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItemByQuery(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam(name = "text") String query) {
        log.info("Searching items by query {} - Started", query);
        ResponseEntity<Object> foundItems = itemClient.searchItemsByQuery(userId, query);
        log.info("Searching items by query {} - Finished", query);
        return foundItems;
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestBody CommentDto commentDto, @RequestHeader("X-Sharer-User-Id") Long userId,
                                 @PathVariable Long itemId) {
        log.info("Adding comment for item with id {} - Started", itemId);
        ResponseEntity<Object> comment = itemClient.createComment(userId, itemId, commentDto);
        log.info("Adding comment for item with id {} - Finished", itemId);
        return comment;
    }
}
