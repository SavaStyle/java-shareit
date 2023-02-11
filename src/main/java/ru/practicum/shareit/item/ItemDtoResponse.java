package ru.practicum.shareIt.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareIt.booking.BookingDtoResponse;
import ru.practicum.shareIt.item.comments.CommentDto;
import ru.practicum.shareIt.user.User;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemDtoResponse {
    private Long id;
    private User owner;
    private Long request;
    private String name;
    private Boolean available;
    private String description;
    private BookingDtoResponse lastBooking;
    private BookingDtoResponse nextBooking;
    private List<CommentDto> comments;

    public ItemDtoResponse(Long id, User owner, Long request, String name, Boolean available, String description) {
        this.id = id;
        this.owner = owner;
        this.request = request;
        this.name = name;
        this.available = available;
        this.description = description;
    }
}
