package ru.practicum.shareIt.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareIt.exception.NotFoundException;
import ru.practicum.shareIt.user.Interfaces.UserRepository;

import java.util.Collections;
import java.util.List;

import static ru.practicum.shareIt.item.ItemMapper.fromItemDto;

@Service
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;

    @Override
    public List<Item> getItemsByOwnerId(long userId) {
        return repository.getItemsByOwnerId(userId);
    }

    @Override
    public Item addNewItem(long userId, ItemDto itemDto) {
        userRepository.getById(userId);
        Item item = fromItemDto(itemDto);
        item.setOwnerId(userId);
        return repository.save(item);
    }

    @Override
    public Item updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item item = repository.getItemById(itemId).orElseThrow(() -> {
            throw new NotFoundException("Item не найден");
        });
        userRepository.getById(userId);
        if (!item.getOwnerId().equals(userId)) {
            throw new NotFoundException("Owner не найден");
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        return repository.updateItem(userId, itemId, item);
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        repository.deleteByUserIdAndItemId(userId, itemId);
    }

    @Override
    public Item getItemById(long itemId) {
        return repository.getItemById(itemId).orElseThrow(() -> {
            throw new NotFoundException("Item не найден");
        });
    }

    @Override
    public List<Item> search(String text) {
        if (text.isBlank())
            return Collections.emptyList();
        return repository.search(text);
    }
}