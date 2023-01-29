package ru.practicum.shareIt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareIt.user.Interfaces.Create;
import ru.practicum.shareIt.user.Interfaces.Update;
import ru.practicum.shareIt.user.Interfaces.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDto saveUser(@Validated({Create.class}) @RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@Validated(Update.class) @RequestBody UserDto userDto, @PathVariable String userId) {
        return userService.updateUser(userDto, Long.parseLong(userId));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(Long.parseLong(userId));
    }

    @GetMapping("/{userId}")
    public UserDto getById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }
}