package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "User name should not be empty")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be blank")
    private String email;
}
