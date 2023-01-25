package ru.practicum.item;

import lombok.Data;
import ru.practicum.request.ItemRequest;

@Data
public class Item {
    private Long id;
    private Long userId;
    private ItemRequest request;
    private String name;
    private String description;
    private boolean available;
}