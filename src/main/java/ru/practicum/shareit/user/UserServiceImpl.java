package ru.practicum.shareIt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareIt.user.Interfaces.UserRepository;
import ru.practicum.shareIt.user.Interfaces.UserService;

import java.util.Collection;

import static ru.practicum.shareIt.user.UserMapper.fromUserDto;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public Collection<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User saveUser(UserDto userDto) {
        User user = fromUserDto(userDto);
        repository.chekEmail(user.getEmail());
        return repository.save(user);
    }

    @Override
    public User updateUser(UserDto userDto, long userId) {
        User user = getUserById(userId);
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            repository.chekEmail(userDto.getEmail());
            user.setEmail(userDto.getEmail());
        }
        return repository.update(user);
    }

    @Override
    public void deleteUser(long userId) {
        repository.deleteUser(userId);
    }

    @Override
    public User getUserById(long userId) {
        return repository.getById(userId);
    }
}