package ru.practicum.shareIt.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareIt.item.Item;
import ru.practicum.shareIt.user.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDtoResponse {
    private Long id;
    private Item item;
    private LocalDateTime start;
    private LocalDateTime end;
    private User booker;
    private BookingStatus status;
    private Long bookerId;
}
