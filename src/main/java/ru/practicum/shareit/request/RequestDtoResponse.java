package ru.practicum.shareIt.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareIt.item.ItemDtoResponse;
import ru.practicum.shareIt.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDtoResponse {

    private Long id;
    private String description;
    private User requestor;
    private LocalDateTime created;
    private List<ItemDtoResponse> items;

    public RequestDtoResponse(Long id, String description, User requestor, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.requestor = requestor;
        this.created = created;
    }
}
