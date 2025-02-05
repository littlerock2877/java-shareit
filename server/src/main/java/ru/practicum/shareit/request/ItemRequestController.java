package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@Slf4j
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService requestService;

    @PostMapping
    public ItemRequestDto create(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Creating request by user with id {} - Started", userId);
        itemRequestDto = requestService.addRequest(userId, itemRequestDto);
        log.info("Creating request by user with id {} - Finished", userId);
        return itemRequestDto;
    }

    @GetMapping
    public List<ItemRequestDto> getAllItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Getting all item requests of user with id {} - Started", userId);
        List<ItemRequestDto> requests  = requestService.getAllItemRequests(userId);
        log.info("Getting all item requests of user with id {} - Finished", userId);
        return requests;
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequestById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long requestId) {
        log.info("Getting item request with id {} - Started", requestId);
        ItemRequestDto itemRequestDto = requestService.getItemRequestById(userId, requestId);;
        log.info("Getting item request with id {} - Finished", requestId);
        return itemRequestDto;
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequestsForUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Getting all item requests for user with id {} - Started", userId);
        List<ItemRequestDto> requests  = requestService.getAllItemRequestsForUser(userId);
        log.info("Getting all item requests for user with id {} - Finished", userId);
        return requests;
    }
}
