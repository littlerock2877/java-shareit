package ru.practicum.shareit.booking.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.client.BookingClient;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.State;

@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@RequiredArgsConstructor
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody BookingDto bookingDto) {
        log.info("Creating booking {} - Started", bookingDto);
        ResponseEntity<Object> booking = bookingClient.createBooking(bookingDto, userId);
        log.info("Creating booking {} - Finished", bookingDto);
        return booking;
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId,
                                     @RequestParam(name = "approved") @NotNull Boolean approved) {
        log.info("Approving booking with id {} - Started", bookingId);
        ResponseEntity<Object> booking = bookingClient.approveBooking(userId, bookingId, approved);
        log.info("Approving booking with id {} - Finished", bookingId);
        return booking;
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId) {
        log.info("Getting booking with id {} - Started", bookingId);
        ResponseEntity<Object> booking = bookingClient.getBooking(userId, bookingId);
        log.info("Getting booking with id {} - Finished", bookingId);
        return booking;
    }

    @GetMapping
    public ResponseEntity<Object> getAllBookingsByBooker(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                         @RequestParam(name = "state", defaultValue = "ALL") State state) {
        log.info("Getting booking for booker with id {} - Started", userId);
        ResponseEntity<Object> bookings = bookingClient.findAllByBooker(userId, state);
        log.info("Getting booking for booker with id {} - Finished", userId);
        return bookings;
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @RequestParam(name = "state", defaultValue = "ALL") State state) {
        log.info("Getting booking for owner with id {} - Started", userId);
        ResponseEntity<Object> bookings = bookingClient.findAllByOwner(userId, state);
        log.info("Getting booking for owner with id {} - Finished", userId);
        return bookings;
    }
}
