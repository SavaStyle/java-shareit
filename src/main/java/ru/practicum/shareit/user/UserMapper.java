package ru.practicum.shareIt.user;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public User fromUserDto(UserDto userDto) {
        return new User(userDto.getName(), userDto.getEmail());
    }
}
