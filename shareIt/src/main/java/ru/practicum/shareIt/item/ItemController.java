package ru.practicum.shareIt.item;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareIt.user.Interfaces.Create;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<Item> getItemsByOwnerId(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItemsByOwnerId(userId);
    }

    @PostMapping
    public Item addNewItem(@RequestHeader("X-Sharer-User-Id") Long userId, @Validated(Create.class) @RequestBody ItemDto itemDto) {
        return itemService.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public Item update(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId, @RequestBody ItemDto itemDto) {
        return itemService.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public Item findItem(@PathVariable long itemId) {
        return itemService.getItemById(itemId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId) {
        itemService.deleteItem(userId, itemId);
    }

    @GetMapping("/search")
    public List<Item> searchItem(@RequestParam String text) {
        return itemService.search(text.toLowerCase());
    }
}