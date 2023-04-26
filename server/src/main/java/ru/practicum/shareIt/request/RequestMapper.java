package ru.practicum.shareIt.request;

import lombok.AllArgsConstructor;
import ru.practicum.shareIt.user.User;

@AllArgsConstructor
public class RequestMapper {


    public static Request fromRequestDto(RequestDto requestDto, User user) {

        return new Request(requestDto.getId(),
                requestDto.getDescription(),
                user,
                requestDto.getCreated());
    }

    public static RequestDtoResponse toRequestDto(Request request) {
        return new RequestDtoResponse(request.getId(),
                request.getDescription(),
                request.getRequestor().getId(),
                request.getCreated());
    }

    public static RequestDto toRequestDtoDto(Request request) {
        return new RequestDto(request.getId(),
                request.getDescription(),
                request.getRequestor().getId(),
                request.getCreated());
    }

    public static RequestDtoResponse toRequestDtoTest(RequestDto requestDto) {
        return new RequestDtoResponse(requestDto.getId(),
                requestDto.getDescription(),
                requestDto.getRequestorId(),
                requestDto.getCreated());
    }
}
