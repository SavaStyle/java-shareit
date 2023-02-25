package ru.practicum.shareIt.item;

import ru.practicum.shareIt.item.comments.CommentDto;

import java.util.List;

public interface ItemService {
    List<ItemDtoResponse> getItemsByOwnerId(long userId, int from, int size);

    ItemDtoResponse addNewItem(long userId, ItemDto itemDto);

    ItemDtoResponse updateItem(Long userId, Long id, ItemDto itemDto);

    void deleteItem(long userId, long itemId);

    ItemDtoResponse getItemById(long userId, long itemId);

    List<ItemDtoResponse> search(String text, int from, int size);

    CommentDto addComment(long userId, CommentDto commentDto, long itemId);

    List<ItemDtoResponse> getItemsByRequestId(long requestId);
}