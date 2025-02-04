package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.Booking;
import java.util.List;

@Data
@AllArgsConstructor
public class ItemDtoWithComments {
    private Long id;

    @NotBlank(message = "Item name should not be null and should contains at least one symbol")
    private String name;

    @NotBlank(message = "Item description should not be null and should contains at last one symbol")
    private String description;

    @NotNull
    private Boolean available;

    Booking lastBooking;

    Booking nextBooking;

    private List<CommentDto> comments;
}