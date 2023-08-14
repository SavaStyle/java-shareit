package ru.practicum.shareIt.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareIt.item.comments.CommentDto;
import ru.practicum.shareIt.user.Interfaces.Create;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping
    public ResponseEntity<Object> getItemsByOwnerId(@RequestHeader("X-Sharer-User-Id") long id,
                                                    @PositiveOrZero @RequestParam(defaultValue = "0", required = false) int from,
                                                    @Positive @RequestParam(defaultValue = "20", required = false) int size) {
        return itemClient.getItemsByOwnerId(id, from, size);
    }

    @PostMapping
    public ResponseEntity<Object> addNewItem(@RequestHeader("X-Sharer-User-Id") long userId, @Validated(Create.class) @RequestBody ItemDto itemDto) {
        return itemClient.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId, @RequestBody ItemDto itemDto) {
        return itemClient.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> findItem(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId) {
        return itemClient.findItem(userId, itemId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable long id) {
        return itemClient.deleteItem(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @RequestParam String text,
                                             @PositiveOrZero @RequestParam(defaultValue = "0", required = false) int from,
                                             @Positive @RequestParam(defaultValue = "20", required = false) int size) {
        return itemClient.searchItem(text, userId, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable long itemId,
                                             @Validated(Create.class) @RequestBody CommentDto commentDto) {
        return itemClient.addComment(userId, itemId, commentDto);
    }
}