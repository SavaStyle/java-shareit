package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public Map<Long, User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User saveNewUser( @RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @PutMapping
    public User updateUser( @RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(Long.parseLong(userId));
    }
}