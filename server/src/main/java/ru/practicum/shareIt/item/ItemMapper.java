package ru.practicum.shareIt.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {

    public static Item fromItemDto(ItemDto itemDto) {
        Item item = new Item(
                itemDto.getRequestId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable());
        return item;
    }

    public static ItemDtoResponse toItemDto(Item item) {
        ItemDtoResponse itemDtoResponse = new ItemDtoResponse(
                item.getId(),
                item.getOwner(),
                item.getRequestId(),
                item.getName(),
                item.getAvailable(),
                item.getDescription());
        return itemDtoResponse;
    }

    public static ItemDto toItemDtoDto(Item item) {
        ItemDto itemDto = new ItemDto(
                item.getId(),
                item.getOwner().getId(),
                item.getRequestId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable());
        return itemDto;
    }

    public static ItemDtoResponse toItemDtoTest(ItemDto itemDto) {
        ItemDtoResponse itemDtoResponse = new ItemDtoResponse(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getAvailable(),
                itemDto.getDescription());
        return itemDtoResponse;
    }
}
