package ru.practicum.shareIt.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareIt.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareIt.request.RequestMapper.toRequestDtoTest;


@WebMvcTest(controllers = RequestController.class)
class RequestControllerTest {
    @MockBean
    RequestService requestService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;
    private User userDto;
    private RequestDto requestDto;

    @BeforeEach
    void start() {

        userDto = new User(
                1L,
                "John",
                "john.doe@mail.com");


        requestDto = new RequestDto(
                1L,
                "description",
                userDto,
                LocalDateTime.now()
        );
    }

    @Test
    @SneakyThrows
    void create_ok() {
        when(requestService.addNewRequest(userDto.getId(), requestDto)).thenReturn(toRequestDtoTest(requestDto));

        mvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", userDto.getId())
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestDto.getId()))
                .andExpect(jsonPath("$.requestor").value(requestDto.getRequestor()))
                .andExpect(jsonPath("$.description").value(requestDto.getDescription()));
    }

    @Test
    @SneakyThrows
    void getAllByRequestorId() {
        when(requestService.getAllByRequestorId(userDto.getId())).thenReturn(List.of(toRequestDtoTest(requestDto)));

        mvc.perform(get("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", userDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(requestDto.getId()))
                .andExpect(jsonPath("$[0].requestor").value(requestDto.getRequestor()))
                .andExpect(jsonPath("$[0].description").value(requestDto.getDescription()));
    }

    @Test
    @SneakyThrows
    void getAll() {
        when(requestService.getAll(anyLong(), anyInt(), anyInt())).thenReturn(List.of(toRequestDtoTest(requestDto)));

        mvc.perform(get("/requests/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", userDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(requestDto.getId()))
                .andExpect(jsonPath("$[0].requestor").value(requestDto.getRequestor()))
                .andExpect(jsonPath("$[0].description").value(requestDto.getDescription()));
    }

    @Test
    @SneakyThrows
    void getById() {
        when(requestService.findById(requestDto.getId(), userDto.getId())).thenReturn(toRequestDtoTest(requestDto));

        mvc.perform(get("/requests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", userDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestDto.getId()))
                .andExpect(jsonPath("$.requestor").value(requestDto.getRequestor()))
                .andExpect(jsonPath("$.description").value(requestDto.getDescription()));
    }
}