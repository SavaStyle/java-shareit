package ru.practicum.shareIt.item;

import java.util.List;

interface ItemService {
    Item addNewItem(long userId, ItemDto itemDto);

    List<Item> getItemsByOwnerId(long userId);

    Item updateItem(Long userId, Long id, ItemDto itemDto);

    void deleteItem(long userId, long itemId);

    Item getItemById(long itemId);

    List<Item> search(String text);
}