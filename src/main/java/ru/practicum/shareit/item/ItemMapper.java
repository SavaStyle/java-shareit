package ru.practicum.shareIt.item;

public class ItemMapper {

    public static Item fromItemDto(ItemDto itemDto) {
        Item item = new Item(itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable());
        return item;
    }
}
