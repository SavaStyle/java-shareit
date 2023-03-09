package ru.practicum.shareIt.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareIt.exception.NotFoundException;
import ru.practicum.shareIt.item.ItemService;
import ru.practicum.shareIt.user.User;
import ru.practicum.shareIt.user.UserRepository;
import ru.practicum.shareIt.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static ru.practicum.shareIt.request.RequestMapper.toRequestDto;
import static ru.practicum.shareIt.request.RequestMapper.toRequestDtoDto;
import static ru.practicum.shareIt.user.UserMapper.toUserDto;

@ExtendWith(MockitoExtension.class)
class RequestServiceImplTest {
    @InjectMocks
    private RequestServiceImpl requestService;
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private ItemService itemService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;

    private Request request;
    private User user;


    @BeforeEach
    void start() {
        user = new User(1L, "John", "john.doe@mail.com");

        request = new Request(1L, "description", user, LocalDateTime.now());
    }

    @Test
    void addNewRequest_ok() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(requestRepository.save(any())).thenReturn(request);

        RequestDtoResponse requestDto = toRequestDto(request);

        RequestDtoResponse response = requestService.addNewRequest(user.getId(), toRequestDtoDto(request));
        assertNotNull(response);
        assertEquals(requestDto, response);
    }

    @Test
    void addNewRequest_userNotFound() {
        when(userRepository.findById(anyLong())).thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> {
            requestService.addNewRequest(user.getId(), toRequestDtoDto(request));
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getAllByRequestorId_userNotFound() {
        when(userService.getUserById(anyLong())).thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> {
            requestService.getAllByRequestorId(user.getId());
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getAllByRequestorId_ok() {
        when(requestRepository.getAllByRequestorId(anyLong())).thenReturn(List.of(request));
        when(userService.getUserById(anyLong())).thenReturn(toUserDto(user));

        RequestDtoResponse requestDto = toRequestDto(request);
        requestDto.setItems(new ArrayList<>());

        List<RequestDtoResponse> response = requestService.getAllByRequestorId(request.getRequestor().getId());

        assertNotNull(response);
        assertEquals(List.of(requestDto), response);
    }

    @Test
    void getAll_userNotFound() {
        when(userService.getUserById(anyLong())).thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> {
            requestService.getAll(user.getId(), 0, 10);
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getAll_ok() {
        when(requestRepository.findAllByRequestorIdIsNotOrderByCreatedDesc(anyLong(), any())).thenReturn(List.of(request));

        RequestDtoResponse requestDto = toRequestDto(request);
        requestDto.setItems(new ArrayList<>());

        List<RequestDtoResponse> responses = requestService.getAll(anyLong(), 0, 10);

        assertNotNull(responses);
        assertEquals(List.of(requestDto), responses);
    }

    @Test
    void itemsSet() {

    }

    @Test
    void findById() {
        when(userService.getUserById(anyLong())).thenReturn(toUserDto(user));
        when(requestRepository.findById(anyLong())).thenReturn(Optional.ofNullable(request));

        RequestDtoResponse requestDto = toRequestDto(request);
        requestDto.setItems(new ArrayList<>());

        RequestDtoResponse responses = requestService.findById(request.getId(), user.getId());
        assertEquals(requestDto, responses);
    }
}