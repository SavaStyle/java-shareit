package ru.practicum.shareIt.item;

import java.util.List;
import java.util.Optional;

interface ItemRepository {

    List<Item> getItemsByOwnerId(long userId);

    Item save(Item item);

    void deleteByUserIdAndItemId(long userId, long itemId);

    Item updateItem(long userId, Long itemId, Item newItem);

    Optional<Item> getItemById(long itemId);

    List<Item> search(String text);
}