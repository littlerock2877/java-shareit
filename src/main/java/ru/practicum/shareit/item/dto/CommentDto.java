package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;

    @NotBlank(message = "Comment text could not be blank")
    private String text;

    private String authorName;
    private LocalDateTime created;
}