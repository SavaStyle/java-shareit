package ru.practicum.item;

import java.util.List;

interface ItemService {
    Item addNewItem(long userId, Item item);

    List<Item> getItems(long userId);

    Item updateItem(long userId, Item item);

    void deleteItem(long userId, long itemId);

    Item getItemById(long itemId);

    List<Item> search(String text);
}