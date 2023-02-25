package ru.practicum.shareIt.request;

import java.util.List;

public interface RequestService {
    RequestDtoResponse addNewRequest(long userId, RequestDto requestDto);

    List<RequestDtoResponse> getAllByRequestorId(long id);

    List<RequestDtoResponse> getAll(long userId, int from, int size);

    RequestDtoResponse itemsSet(RequestDtoResponse requestDtoResponse);

    RequestDtoResponse findById(long id, long userId);

}
