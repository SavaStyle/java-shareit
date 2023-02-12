package ru.practicum.shareIt.item;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemMapper {

    public Item fromItemDto(ItemDto itemDto) {
        Item item = new Item(itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable());
        return item;
    }

    public ItemDtoResponse toItemDto(Item item) {
        ItemDtoResponse itemDtoResponse = new ItemDtoResponse(
                item.getId(),
                item.getOwner(),
                item.getRequest(),
                item.getName(),
                item.getAvailable(),
                item.getDescription());
        return itemDtoResponse;
    }
}
