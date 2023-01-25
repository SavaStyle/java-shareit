package ru.practicum.shareIt.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareIt.exception.EmailException;
import ru.practicum.shareIt.exception.NotFoundException;
import ru.practicum.shareIt.user.Interfaces.UserRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    private long id = 0;

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User save(User user) {
        user.setId(getId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUser(long userId) {
        users.remove(userId);
    }

    private long getId() {
        return ++id;
    }

    @Override
    public void chekEmail(String email) {
        if (users.values()
                .stream()
                .map(User::getEmail)
                .anyMatch(email::equals)) {
            throw new EmailException("EmailException");
        }
    }

    @Override
    public User getById(long userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        } else {
            throw new NotFoundException("User не найден");
        }
    }

    @Override
    public boolean isContains(long userId) {
        return users.containsKey(userId);
    }
}