package ru.practicum.shareIt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareIt.user.Interfaces.Create;
import ru.practicum.shareIt.user.Interfaces.Update;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return userClient.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@Validated(Create.class) @RequestBody UserDto userDto) {
        return userClient.saveUser(userDto);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable long id, @Validated(Update.class) @RequestBody UserDto userDto) {
        return userClient.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userClient.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return userClient.getById(id);
    }
}