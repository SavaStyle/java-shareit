package ru.practicum.shareIt.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareIt.user.Interfaces.Create;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @Validated(Create.class) @RequestBody RequestDto requestDto) {
        return requestClient.create(userId, requestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllByRequestorId(@RequestHeader("X-Sharer-User-Id") long userId) {
        return requestClient.getAllByRequestorId(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @PositiveOrZero @RequestParam(defaultValue = "0", required = false) int from,
                                         @Positive @RequestParam(defaultValue = "10", required = false) int size) {
        return requestClient.getAll(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getById(@RequestHeader("X-Sharer-User-Id") long userId,
                                          @PathVariable long requestId) {
        return requestClient.getById(userId, requestId);
    }
}
