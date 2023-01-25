package ru.practicum.shareIt.item;

import lombok.Data;

import java.util.Objects;

@Data
public class Item {
    private Long id;
    private Long ownerId;
    private Long request;
    private String name;
    private String description;
    private Boolean available;

    public Item(String name, String description, boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}