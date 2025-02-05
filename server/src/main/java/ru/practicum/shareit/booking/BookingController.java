package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.service.BookingService;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@RequiredArgsConstructor
@Validated
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingOutputDto createBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody BookingDto bookingDto) {
        log.info("Creating booking {} - Started", bookingDto);
        BookingOutputDto booking = bookingService.createBooking(bookingDto, userId);
        log.info("Creating booking {} - Finished", bookingDto);
        return booking;
    }

    @PatchMapping("/{bookingId}")
    public BookingOutputDto approveBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId,
                                     @RequestParam(name = "approved") Boolean approved) {
        log.info("Approving booking with id {} - Started", bookingId);
        BookingOutputDto bookingDto = bookingService.approveBooking(userId, bookingId, approved);
        log.info("Approving booking with id {} - Finished", bookingId);
        return bookingDto;
    }

    @GetMapping("/{bookingId}")
    public BookingOutputDto getBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId) {
        log.info("Getting booking with id {} - Started", bookingId);
        BookingOutputDto bookingDto = bookingService.getBooking(userId, bookingId);
        log.info("Getting booking with id {} - Finished", bookingId);
        return bookingDto;
    }

    @GetMapping
    public List<BookingOutputDto> getAllBookingsByBooker(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                         @RequestParam(name = "state", defaultValue = "ALL") State state) {
        log.info("Getting booking for booker with id {} - Started", userId);
        List<BookingOutputDto> bookings = bookingService.findAllByBooker(userId, state);
        log.info("Getting booking for booker with id {} - Finished", userId);
        return bookings;
    }

    @GetMapping("/owner")
    public List<BookingOutputDto> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @RequestParam(name = "state", defaultValue = "ALL") State state) {
        log.info("Getting booking for owner with id {} - Started", userId);
        List<BookingOutputDto> bookings = bookingService.findAllByOwner(userId, state);
        log.info("Getting booking for owner with id {} - Finished", userId);
        return bookings;
    }
}
