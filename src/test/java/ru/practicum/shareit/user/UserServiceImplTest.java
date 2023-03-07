package ru.practicum.shareIt.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareIt.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.practicum.shareIt.user.UserMapper.fromUserDto;
import static ru.practicum.shareIt.user.UserMapper.toUserDto;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;
    private UserDto userDto;
    private User user;
    @BeforeEach
    void start() {
        userDto = new UserDto(
                1L,
                "John",
                "john.doe@mail.com");

        user = new User(
                1L,
                "John",
                "john.doe@mail.com");
    }

    @Test
    void getAllUsers_ok() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        assertEquals(userService.getAllUsers(), List.of(toUserDto(user)));
    }

    @Test
    void saveUser_ok() {
        when(userRepository.save(any())).thenReturn(fromUserDto(userDto));

        UserDto userDto1 = userService.saveUser(userDto);

        assertEquals(userDto, userDto1);
    }

    @Test
    void updateUser_ok() {
        User userU = new User();
        userU.setId(5L);
        userU.setName("1");
        userU.setEmail("2.doe@mail.com");
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(userU));
        when(userRepository.save(any(User.class))).thenReturn(userU);

        UserDto userDto1 = UserMapper.toUserDto(user);
        userService.updateUser(userDto1, userDto.getId());

        assertEquals("John", userU.getName());
        assertEquals("john.doe@mail.com", userU.getEmail());
    }

    @Test
    void updateUser_notFounded() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(userDto.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getUserById_ok() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));

        assertEquals(userService.getUserById(user.getId()), toUserDto(user));
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(99)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void deleteUser() {
        userService.deleteUser(userDto.getId());

        verify(userRepository).deleteById(userDto.getId());
    }
}