package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.validation.EndAfterStart;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EndAfterStart
public class BookingDto {
    private Long id;

    @FutureOrPresent(message = "Booking start time could not be in past")
    private LocalDateTime start;

    @Future(message = "Booking end time should be in future")
    private LocalDateTime end;

    private Long itemId;

    private Long bookerId;

    private BookingStatus status;
}
