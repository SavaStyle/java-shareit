package ru.practicum.shareIt.item;

import java.util.List;

interface ItemService {
    ItemDto addNewItem(long userId, ItemDto itemDto);

    List<ItemDto>  getItemsByOwnerId(long userId);

    ItemDto updateItem(Long userId, Long id, ItemDto itemDto);

    void deleteItem(long userId, long itemId);

    ItemDto getItemById(long itemId);

    List<ItemDto> search(String text);
}