package ru.practicum.shareIt.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareIt.item.Item;
import ru.practicum.shareIt.user.User;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareIt.booking.BookingMapper.toBookingDtoTest;
import static ru.practicum.shareIt.booking.BookingStatus.REJECTED;
import static ru.practicum.shareIt.booking.BookingStatus.WAITING;


@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {
    @MockBean
    BookingService bookingService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    private Item itemDto;
    private User userDto;

    private BookingDto bookingDto;

    @BeforeEach
    void start() {

        userDto = new User(
                1L,
                "John",
                "john.doe@mail.com");


        itemDto = new Item(
                1L,
                userDto,
                1L,
                "nameItem",
                "description",
                true);

        bookingDto = new BookingDto(
                1l,
                null,
                null,
                1L,
                1L,
                WAITING
        );
    }

    @Test
    @SneakyThrows
    void create_ok() {
        BookingDtoResponse bookingDtoResponse = toBookingDtoTest(bookingDto, itemDto, userDto);

        when(bookingService.addNewBooking(userDto.getId(), bookingDto)).thenReturn(bookingDtoResponse);

        mvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", userDto.getId())
                        .content(mapper.writeValueAsString(bookingDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.id").value(userDto.getId()))
                .andExpect(jsonPath("$.item.name").value(itemDto.getName()))
                .andExpect(jsonPath("$.booker.email").value(userDto.getEmail()));
    }


    @Test
    @SneakyThrows
    void changeStatus() {
        BookingDtoResponse bookingDtoResponse = toBookingDtoTest(bookingDto, itemDto, userDto);
        bookingDtoResponse.setStatus(REJECTED);
        when(bookingService.changeStatus(userDto.getId(), bookingDto.getId(), false)).thenReturn(bookingDtoResponse);

        mvc.perform(patch("/bookings/1?approved=false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", userDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("REJECTED"));
    }

    @Test
    @SneakyThrows
    void getById() {
        BookingDtoResponse bookingDtoResponse = toBookingDtoTest(bookingDto, itemDto, userDto);

        when(bookingService.getById(anyLong(), anyLong())).thenReturn(bookingDtoResponse);

        mvc.perform(get("/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", userDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.id").value(userDto.getId()))
                .andExpect(jsonPath("$.item.name").value(itemDto.getName()))
                .andExpect(jsonPath("$.booker.email").value(userDto.getEmail()));

    }

    @Test
    @SneakyThrows
    void getAllByBooker() {
        BookingDtoResponse bookingDtoResponse = toBookingDtoTest(bookingDto, itemDto, userDto);

        when(bookingService.getAllByBooker(1L, "ALL", 0, 10)).thenReturn(List.of(bookingDtoResponse));

        mvc.perform(get("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].item.id").value(userDto.getId()))
                .andExpect(jsonPath("$[0].item.name").value(itemDto.getName()))
                .andExpect(jsonPath("$[0].booker.email").value(userDto.getEmail()));
    }

    @Test
    @SneakyThrows
    void getByOwner() {
        BookingDtoResponse bookingDtoResponse = toBookingDtoTest(bookingDto, itemDto, userDto);

        when(bookingService.getAllByOwner(1L, "ALL", 0, 10)).thenReturn(List.of(bookingDtoResponse));

        mvc.perform(get("/bookings/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].item.id").value(userDto.getId()))
                .andExpect(jsonPath("$[0].item.name").value(itemDto.getName()))
                .andExpect(jsonPath("$[0].booker.email").value(userDto.getEmail()));
    }
}