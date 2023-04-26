package ru.practicum.shareIt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StatusException extends RuntimeException {
    public StatusException() {
        super("Unknown state: UNSUPPORTED_STATUS");
    }
}