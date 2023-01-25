package ru.practicum.shareIt.item;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, List<Item>> items = new HashMap<>();

    @Override
    public List<Item> getItemsByOwnerId(long userId) {
        return items.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public Item save(Item item) {
        item.setId(getId());
        items.compute(item.getOwnerId(), (userId, userItems) -> {
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
        items.get(userId).removeIf(i -> i.getId().equals(itemId));
    }

    @Override
    public Item updateItem(long userId, Long itemId, Item newItem) {
        Item repoItem = items.get(userId).stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .get();
        items.get(userId).removeIf(i -> i.getId().equals(newItem.getId()));
        items.get(userId).add(repoItem);
        return repoItem;
    }

    @Override
    public Optional<Item> getItemById(long itemId) {
        List<Item> allItems = new ArrayList<>();
        items.forEach((u, i) -> allItems.addAll(i));
        return allItems.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst();
    }

    @Override
    public List<Item> search(String text) {
        List<Item> allItems = new ArrayList<>();
        items.forEach((userId, i) -> allItems.addAll(items.get(userId)));
        return allItems.stream()
                .filter(i -> i.getName().toLowerCase().contains(text) ||
                        i.getDescription().toLowerCase().contains(text))
                .filter(Item::getAvailable)
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