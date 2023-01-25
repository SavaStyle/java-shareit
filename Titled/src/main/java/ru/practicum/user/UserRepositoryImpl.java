package ru.practicum.user;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Map<Long, User> findAll() {
        return users;
    }

    @Override
    public User save(User user) {
        user.setId(getId());
      if (!chekEmail(user)) {
          users.put(user.getId(), user);
      } else {
          throw new RuntimeException("Пользователь с таким email уже существует");
      }
        return user;
    }

    @Override
    public User update(User user) {
        users.remove(user.getId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUser(long userId) {
        users.remove(userId);
    }

    private long getId() {
        return users.keySet().size() + 1;
    }
    @Override
    public boolean chekEmail(User user) {
        return users.values()
                .stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()));
    }
}