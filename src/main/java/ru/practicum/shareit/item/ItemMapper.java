package ru.practicum.shareIt.item;

public class ItemMapper {

    public static Item fromItemDto(ItemDto itemDto) {
        Item item = new Item(itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable());
        return item;
    }

    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable());
        return itemDto;
    }
}
