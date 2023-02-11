package ru.practicum.shareIt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareIt.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareIt.user.UserMapper.fromUserDto;
import static ru.practicum.shareIt.user.UserMapper.toUserDto;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getAllUsers() {
        return repository
                .findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = fromUserDto(userDto);
        repository.save(user);
        return toUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, long userId) {
        User user = repository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("User not found " + userId);
        });
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        repository.save(user);
        return toUserDto(user);
    }

    @Override
    public void deleteUser(long userId) {
        repository.deleteById(userId);
    }

    @Override
    public UserDto getUserById(long userId) {
        User user = repository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("User not found " + userId);
        });
        return toUserDto(user);
    }
}
