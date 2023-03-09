package ru.practicum.shareIt.request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static Request fromRequestDto(RequestDto requestDto) {
        return new Request(requestDto.getId(),
                requestDto.getDescription(),
                requestDto.getRequestor(),
                requestDto.getCreated());
    }

    public static RequestDtoResponse toRequestDto(Request request) {
        return new RequestDtoResponse(request.getId(),
                request.getDescription(),
                request.getRequestor(),
                request.getCreated());
    }

    public static RequestDto toRequestDtoDto(Request request) {
        return new RequestDto(request.getId(),
                request.getDescription(),
                request.getRequestor(),
                request.getCreated());
    }

    public static RequestDtoResponse toRequestDtoTest(RequestDto requestDto) {
        return new RequestDtoResponse(requestDto.getId(),
                requestDto.getDescription(),
                requestDto.getRequestor(),
                requestDto.getCreated());
    }
}
