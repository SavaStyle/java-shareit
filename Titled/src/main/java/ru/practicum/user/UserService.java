package ru.practicum.user;

import java.util.Map;

interface UserService {
    Map<Long, User> getAllUsers();

    User saveUser(UserDto userDto);

    User updateUser(User user);

    void deleteUser(long userId);
}