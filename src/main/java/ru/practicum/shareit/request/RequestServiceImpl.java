package ru.practicum.shareIt.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareIt.exception.BadRequestException;
import ru.practicum.shareIt.exception.NotFoundException;
import ru.practicum.shareIt.item.ItemDtoResponse;
import ru.practicum.shareIt.item.ItemService;
import ru.practicum.shareIt.user.User;
import ru.practicum.shareIt.user.UserDto;
import ru.practicum.shareIt.user.UserRepository;
import ru.practicum.shareIt.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareIt.request.RequestMapper.fromRequestDto;
import static ru.practicum.shareIt.request.RequestMapper.toRequestDto;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    private final UserService userService;

    private final ItemService itemService;

    private final UserRepository userRepository;

    @Override
    public RequestDtoResponse addNewRequest(long userId, RequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("User not found");
        });
        Request request = fromRequestDto(requestDto);
        request.setRequestor(user);
        request.setCreated(LocalDateTime.now());
        return toRequestDto(requestRepository.save(request));
    }

    @Override
    public List<RequestDtoResponse> getAllByRequestorId(long id) {
        UserDto userDto = userService.getUserById(id);
        return requestRepository.getAllByRequestorId(id)
                .stream()
                .map(RequestMapper::toRequestDto)
                .map(this::itemsSet)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDtoResponse> getAll(long userId, int from, int size) {
        UserDto userDto = userService.getUserById(userId);
        if (size <= 0 || from < 0) {
            throw new BadRequestException("параметры запроса страниц не верны");
        }
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);
        return requestRepository.findAllByRequestorIdIsNotOrderByCreatedDesc(userId, pageRequest)
                .stream()
                .map(RequestMapper::toRequestDto)
                .map(this::itemsSet)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDtoResponse itemsSet(RequestDtoResponse requestDtoResponse) {
        List<ItemDtoResponse> items = itemService.getItemsByRequestId(requestDtoResponse.getId());
        requestDtoResponse.setItems(items);
        return requestDtoResponse;
    }

    @Override
    public RequestDtoResponse findById(long id, long userId) {
        UserDto userDto = userService.getUserById(userId);
        Request request = requestRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("запрос не найден");
        });
        return itemsSet(toRequestDto(request));
    }
}

