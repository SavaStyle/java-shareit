package ru.practicum.user;

import java.util.Map;

interface UserRepository {
    Map<Long, User> findAll();

    User save(User user);

    User update(User user);

    void deleteUser(long userId);

    boolean chekEmail(User user);
}