package ru.practicum.shareIt.user;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static User fromUserDto(UserDto userDto) {
        return new User(userDto.getName(), userDto.getEmail());
    }
}
