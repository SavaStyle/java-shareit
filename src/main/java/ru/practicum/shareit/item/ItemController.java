package ru.practicum.shareIt.item;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareIt.item.comments.CommentDto;
import ru.practicum.shareIt.user.Interfaces.Create;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDtoResponse> getItemsByOwnerId(@RequestHeader("X-Sharer-User-Id") long userId,
                                                   @RequestParam(defaultValue = "0") Integer from,
                                                   @RequestParam(defaultValue = "10") Integer size) {
        return itemService.getItemsByOwnerId(userId, from, size);
    }

    @PostMapping
    public ItemDtoResponse addNewItem(@RequestHeader("X-Sharer-User-Id") Long userId, @Validated(Create.class) @RequestBody ItemDto itemDto) {
        return itemService.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDtoResponse update(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId, @RequestBody ItemDto itemDto) {
        return itemService.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDtoResponse findItem(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId) {
        return itemService.getItemById(itemId, userId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId) {
        itemService.deleteItem(userId, itemId);
    }

    @GetMapping("/search")
    public List<ItemDtoResponse> searchItem(@RequestParam String text,
                                            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                            @Positive @RequestParam(defaultValue = "10") Integer size) {
        return itemService.search(text.toLowerCase(), from, size);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(name = "X-Sharer-User-Id") Long userId, @Validated @RequestBody CommentDto commentDto, @PathVariable Long itemId) {
        return itemService.addComment(userId, commentDto, itemId);
    }
}