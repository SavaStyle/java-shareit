package ru.practicum.shareIt.user.Interfaces;

import ru.practicum.shareIt.user.User;
import ru.practicum.shareIt.user.UserDto;

import java.util.Collection;

public interface UserService {
    Collection<User> getAllUsers();

    User saveUser(UserDto userDto);

    User updateUser(UserDto userDto, long userId);

    void deleteUser(long userId);

    User getUserById(long userId);
}