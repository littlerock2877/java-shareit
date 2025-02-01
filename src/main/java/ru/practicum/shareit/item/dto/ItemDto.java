package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDto {
    private Long id;

    @NotBlank(message = "Item name should not be null and should contains at least one symbol")
    private String name;

    @NotBlank(message = "Item description should not be null and should contains at last one symbol")
    private String description;

    @NotNull
    private Boolean available;
}
