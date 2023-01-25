package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;

    @Override
    public List<Item> getItems(long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Item addNewItem(long userId, Item item) {
        item.setUserId(userId);
        return repository.save(item);
    }

    @Override
    public Item updateItem(long userId, Item item) {
        if (item.getUserId().equals(userId)) {
            repository.updateItem(userId, item);
        }
        return item;
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        repository.deleteByUserIdAndItemId(userId, itemId);
    }

    @Override
    public Item getItemById(long itemId) {
        return repository.getItemById(itemId);
    }

    @Override
    public List<Item> search(String text) {
        return repository.search(text);
    }
}