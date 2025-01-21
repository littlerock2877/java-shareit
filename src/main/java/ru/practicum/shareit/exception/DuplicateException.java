package ru.practicum.shareit.exception;

import lombok.Getter;

@Getter
public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
}
