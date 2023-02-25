package ru.practicum.shareIt.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public RequestDtoResponse create(@RequestHeader("X-Sharer-User-Id") long userId, @Valid @RequestBody RequestDto requestDto) {
        return requestService.addNewRequest(userId, requestDto);
    }

    @GetMapping
    public List<RequestDtoResponse> getAllByRequestorId(@RequestHeader("X-Sharer-User-Id") long userId) {
        return requestService.getAllByRequestorId(userId);
    }

    @GetMapping("/all")
    public List<RequestDtoResponse> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "1") Integer size) {
        return requestService.getAll(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public RequestDtoResponse getById(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long requestId) {
        return requestService.findById(requestId, userId);
    }
}
