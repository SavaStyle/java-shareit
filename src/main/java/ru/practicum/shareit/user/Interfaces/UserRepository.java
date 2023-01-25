package ru.practicum.shareIt.user.Interfaces;

import ru.practicum.shareIt.user.User;

import java.util.Collection;

public interface UserRepository {
    Collection<User> findAll();

    User save(User user);

    User update(User user);

    void deleteUser(long userId);

    void chekEmail(String email);

    User getById(long id);

    boolean isContains(long userId);
}