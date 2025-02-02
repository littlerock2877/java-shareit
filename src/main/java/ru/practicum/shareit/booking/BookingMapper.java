package ru.practicum.shareit.booking;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

@Component
public class BookingMapper {
    public BookingDto toBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getBooker().getId(),
                booking.getStatus()
        );
    }

    public BookingOutputDto toOutputDto(Booking booking, Item item, User user) {
        return new BookingOutputDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                item,
                user,
                booking.getStatus()
        );
    }

    public Booking toModel(BookingDto bookingDto, User booker, Item item) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                item,
                booker,
                bookingDto.getStatus()
        );
    }
}