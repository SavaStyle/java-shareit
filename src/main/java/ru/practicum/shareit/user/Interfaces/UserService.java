package ru.practicum.shareIt.user.Interfaces;

import ru.practicum.shareIt.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto saveUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, long userId);

    void deleteUser(long userId);

    UserDto getUserById(long userId);
}