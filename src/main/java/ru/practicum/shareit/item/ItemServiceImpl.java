package ru.practicum.shareIt.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareIt.exception.NotFoundException;
import ru.practicum.shareIt.user.Interfaces.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareIt.item.ItemMapper.fromItemDto;
import static ru.practicum.shareIt.item.ItemMapper.toItemDto;

@Service
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;

    @Override
    public List<ItemDto> getItemsByOwnerId(long userId) {
        return repository
                .getItemsByOwnerId(userId)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto addNewItem(long userId, ItemDto itemDto) {
        userRepository.getById(userId);
        Item item = fromItemDto(itemDto);
        item.setOwnerId(userId);
        repository.save(item);
        return toItemDto(item);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
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
        repository.updateItem(userId, itemId, item);
        return toItemDto(item);
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        repository.deleteByUserIdAndItemId(userId, itemId);
    }

    @Override
    public ItemDto getItemById(long itemId) {
        return toItemDto(repository.getItemById(itemId).orElseThrow(() -> {
            throw new NotFoundException("Item не найден");
        }));
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text.isBlank())
            return Collections.emptyList();
        return repository
                .search(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}