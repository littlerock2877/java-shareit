package ru.practicum.shareit.booking.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import java.util.List;

@Transactional(readOnly = true)
public interface BookingService {
    @Transactional
    BookingOutputDto createBooking(BookingDto bookingDto, Long userId);

    @Transactional
    BookingOutputDto approveBooking(Long userId, Long bookingId, Boolean approved);

    BookingOutputDto getBooking(Long userId, Long bookingId);

    List<BookingOutputDto> findAllByBooker(Long userId, State state);

    List<BookingOutputDto> findAllByOwner(Long userId, State state);
}