package ru.practicum.item;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, List<Item>> items = new HashMap<>();

    @Override
    public List<Item> findByUserId(long userId) {
        return items.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public Item save(Item item) {
        item.setId(getId());
        items.compute(item.getUserId(), (userId, userItems) -> {
            if (userItems == null) {
                userItems = new ArrayList<>();
            }
            userItems.add(item);
            return userItems;
        });
        return item;
    }

    @Override
    public void deleteByUserIdAndItemId(long userId, long itemId) {
        if (items.containsKey(userId)) {
            List<Item> userItems = items.get(userId);
            userItems.removeIf(item -> item.getId().equals(itemId));
        }
    }

    @Override
    public Item updateItem(long userId, Item newItem) {
        deleteByUserIdAndItemId(userId, newItem.getId());
        save(newItem);
        return newItem;
    }

    @Override
    public Item getItemById(long itemId){
        return (Item) items.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getId() == itemId);
    }
    @Override
    public List<Item> search(String text) {
        return items.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(Item::isAvailable)
                .filter(i -> i.getName().contains("txt") || i.getDescription().contains("txt"))
                .collect(Collectors.toList());
    }

    private long getId() {
        long lastId = items.values()
                .stream()
                .flatMap(Collection::stream)
                .mapToLong(Item::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}