package ru.practicum.shareIt.item;

import ru.practicum.shareIt.item.comments.CommentDto;

import java.util.List;

interface ItemService {
    ItemDtoResponse addNewItem(long userId, ItemDto itemDto);

    List<ItemDtoResponse> getItemsByOwnerId(long userId);

    ItemDtoResponse updateItem(Long userId, Long id, ItemDto itemDto);

    void deleteItem(long userId, long itemId);

    ItemDtoResponse getItemById(long userId, long itemId);

    List<ItemDtoResponse> search(String text);

    CommentDto addComment(long userId, CommentDto commentDto, long itemId);
}