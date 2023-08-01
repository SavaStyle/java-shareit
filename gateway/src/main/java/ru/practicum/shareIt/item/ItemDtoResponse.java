package ru.practicum.shareIt.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareIt.booking.BookingDto;
import ru.practicum.shareIt.item.comments.CommentDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoResponse {
    private Long id;
    private Long ownerId;
    private Long requestId;
    private String name;
    private Boolean available;
    private String description;
    private BookingDto lastBooking;
    private BookingDto nextBooking;
    private List<CommentDto> comments;

    public ItemDtoResponse(Long id, Long ownerId, Long requestId, String name, Boolean available, String description) {
        this.id = id;
        this.ownerId = ownerId;
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
