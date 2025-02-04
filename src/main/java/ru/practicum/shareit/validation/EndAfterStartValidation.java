package ru.practicum.shareit.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.shareit.booking.dto.BookingDto;

public class EndAfterStartValidation implements ConstraintValidator<EndAfterStart, BookingDto> {
    @Override
    public boolean isValid(BookingDto bookingDto, ConstraintValidatorContext context) {
        if (bookingDto.getStart() == null || bookingDto.getEnd() == null) {
            return false;
        }
        return bookingDto.getEnd().isAfter(bookingDto.getStart());
    }
}
