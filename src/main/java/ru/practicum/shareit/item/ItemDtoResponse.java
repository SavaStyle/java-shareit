package ru.practicum.shareIt.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareIt.booking.BookingDtoResponse;
import ru.practicum.shareIt.item.comments.CommentDto;
import ru.practicum.shareIt.user.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoResponse {
    private Long id;
    private User owner;
    private Long requestId;
    private String name;
    private Boolean available;
    private String description;
    private BookingDtoResponse lastBooking;
    private BookingDtoResponse nextBooking;
    private List<CommentDto> comments;

    public ItemDtoResponse(Long id, User owner, Long requestId, String name, Boolean available, String description) {
        this.id = id;
        this.owner = owner;
        this.requestId = requestId;
        this.name = name;
        this.available = available;
        this.description = description;
    }

    public ItemDtoResponse(Long id, String name, Boolean available, String description) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.description = description;
    }
}
