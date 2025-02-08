package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.client.ItemRequestClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@RestController
@RequestMapping(path = "/requests")
@Slf4j
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Creating request by user with id {} - Started", userId);
        ResponseEntity<Object> request = itemRequestClient.addRequest(userId, itemRequestDto);
        log.info("Creating request by user with id {} - Finished", userId);
        return request;
    }

    @GetMapping()
    public ResponseEntity<Object> getAllItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Getting all item requests of user with id {} - Started", userId);
        ResponseEntity<Object> requests  = itemRequestClient.getAllItemRequests(userId);
        log.info("Getting all item requests of user with id {} - Finished", userId);
        return requests;
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequestById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long requestId) {
        log.info("Getting item request with id {} - Started", requestId);
        ResponseEntity<Object> itemRequestDto = itemRequestClient.getItemRequestById(userId, requestId);;
        log.info("Getting item request with id {} - Finished", requestId);
        return itemRequestDto;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequestsForUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Getting all item requests for user with id {} - Started", userId);
        ResponseEntity<Object> requests  = itemRequestClient.getAllItemRequestsForUser(userId);
        log.info("Getting all item requests for user with id {} - Finished", userId);
        return requests;
    }
}
