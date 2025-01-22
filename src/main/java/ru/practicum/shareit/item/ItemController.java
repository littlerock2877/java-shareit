package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestBody @Valid ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Adding item {} - Started", itemDto);
        itemDto = itemService.addItem(itemDto, userId);
        log.info("Adding item {} - Finished", itemDto);
        return itemDto;
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestBody ItemDto itemDto,
                              @RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId) {
        log.info("Updating item {} - Started", itemDto);
        itemDto = itemService.updateItem(itemDto, itemId, userId);
        log.info("Updating item {} - Finished", itemDto);
        return itemDto;
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable Long itemId) {
        log.info("Getting item with id {} - Started", itemId);
        ItemDto itemDto = itemService.getItem(itemId);
        log.info("Getting item with id {} - Finished", itemId);
        return itemDto;
    }

    @GetMapping
    public List<ItemDto> getOwnedItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Getting owned items for user with id {} - Started", userId);
        List<ItemDto> ownedItems = itemService.getOwnedItems(userId);
        log.info("Getting owned items for user with id {} - Finished", userId);
        return ownedItems;
    }

    @GetMapping("/search")
    public List<ItemDto> searchItemByQuery(@RequestParam(name = "text") String query) {
        log.info("Searching items by query {} - Started", query);
        List<ItemDto> foundItems = itemService.searchItemsByQuery(query);
        log.info("Searching items by query {} - Finished", query);
        return foundItems;
    }
}
