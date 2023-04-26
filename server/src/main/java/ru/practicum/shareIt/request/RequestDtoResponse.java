package ru.practicum.shareIt.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareIt.item.ItemDtoResponse;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDtoResponse {

    private Long id;
    private String description;
    private Long requestorId;
    private LocalDateTime created;
    private List<ItemDtoResponse> items;

    public RequestDtoResponse(Long id, String description, Long requestorId, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.requestorId = requestorId;
        this.created = created;
    }
}
