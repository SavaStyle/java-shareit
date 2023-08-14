package ru.practicum.shareIt.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDtoResponse create(@RequestHeader("X-Sharer-User-Id") long userId, @RequestBody BookingDto bookingDto) {
        return bookingService.addNewBooking(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResponse changeStatus(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long bookingId, @RequestParam boolean approved) {
        return bookingService.changeStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoResponse getById(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long bookingId) {
        return bookingService.getById(userId, bookingId);
    }

    @GetMapping
    public Collection<BookingDtoResponse> getAllByBooker(@RequestHeader("X-Sharer-User-Id") long userId,
                                                         @RequestParam(defaultValue = "0") Integer from,
                                                         @RequestParam(defaultValue = "10") Integer size,
                                                         @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAllByBooker(userId, state, from, size);
    }

    @GetMapping("/owner")
    public Collection<BookingDtoResponse> getByOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                     @RequestParam(defaultValue = "0") Integer from,
                                                     @RequestParam(defaultValue = "10") Integer size,
                                                     @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAllByOwner(userId, state, from, size);
    }
}