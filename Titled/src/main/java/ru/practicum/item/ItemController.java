package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<Item> get(@RequestHeader("X-Later-User-Id") long userId) {
        return itemService.getItems(userId);
    }

    @PostMapping
    public Item add(@RequestHeader("X-Later-User-Id") Long userId,
                    @RequestBody Item item) {
        return itemService.addNewItem(userId, item);
    }

    @PutMapping("/items/{itemId}")
    public Item update(@RequestHeader("X-Later-User-Id") Long userId,
                       @RequestBody Item item) {
        return itemService.updateItem(userId, item);
    }

    @GetMapping("/items/{itemId}")
    public Item getItemById(@PathVariable String itemId) {
        return itemService.getItemById(Long.parseLong(itemId));
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Later-User-Id") long userId,
                           @PathVariable long itemId) {
        itemService.deleteItem(userId, itemId);
    }

    @GetMapping("/items/search?text={text}")
    public List<Item> update(@RequestHeader("X-Later-User-Id") Long userId,
                       @PathVariable String text) {
        return itemService.search(text);
    }

}