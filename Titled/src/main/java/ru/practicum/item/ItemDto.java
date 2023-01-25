package ru.practicum.item;

import lombok.Data;

@Data
public class ItemDto {
    private Long id;
    private Long userId;
    private String request;
    private String name;
    private String description;
    private boolean available;

    public ItemDto(String name, String description, boolean available, Long aLong) {
    }
}
