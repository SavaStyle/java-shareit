package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

import static ru.practicum.user.UserMapper.fromUserDto;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public Map<Long, User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User saveUser(UserDto userDto) {
        User user = fromUserDto(userDto);
        repository.chekEmail(user);
        return repository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return repository.update(user);
    }

    @Override
    public void deleteUser(long userId) {
        repository.deleteUser(userId);
    }
}