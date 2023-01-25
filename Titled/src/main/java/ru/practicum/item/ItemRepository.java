package ru.practicum.item;

import java.util.List;

interface ItemRepository {

    List<Item> findByUserId(long userId);

    Item save(Item item);

    void deleteByUserIdAndItemId(long userId, long itemId);

    Item updateItem(long userId, Item item);

    Item getItemById(long itemId);

    List<Item> search(String text);
}