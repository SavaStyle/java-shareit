package ru.practicum.shareIt.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareIt.exception.NotFoundException;
import ru.practicum.shareIt.item.Item;
import ru.practicum.shareIt.item.ItemRepository;
import ru.practicum.shareIt.user.User;
import ru.practicum.shareIt.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.practicum.shareIt.booking.BookingMapper.toBookingDto;
import static ru.practicum.shareIt.booking.BookingMapper.toBookingDtoDto;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;

    private Booking booking;
    private User user;
    private Item item;

    @BeforeEach
    void start() {
        user = new User(1L, "John", "john.doe@mail.com");

        item = new Item(1L, user, 1L, "name", "description", true);

        booking = new Booking(1L, item, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), user, BookingStatus.WAITING);
    }

    @Test
    void addNewBooking_ok() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.save(any())).thenReturn(booking);

        BookingDtoResponse bookingDto = toBookingDto(booking);

        BookingDtoResponse response = bookingService.addNewBooking(2L, toBookingDtoDto(booking));

        assertEquals(bookingDto, response);
    }

    @Test
    void addNewBooking_error_bookByOwner() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> bookingService.addNewBooking(1L, toBookingDtoDto(booking))).isInstanceOf(NotFoundException.class);
    }

    @Test
    void addNewBooking_userNotFounded() {
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));

        assertThatThrownBy(() -> bookingService.addNewBooking(2L, toBookingDtoDto(booking))).isInstanceOf(NotFoundException.class);
    }

    @Test
    void changeStatus_APPROVED() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        when(bookingRepository.save(any())).thenReturn(booking);

        BookingDtoResponse response = bookingService.changeStatus(item.getOwner().getId(), booking.getId(), true);
        assertNotNull(response);
        assertEquals(response.getStatus(), BookingStatus.APPROVED);
    }

    @Test
    void changeStatus_REJECTED() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        when(bookingRepository.save(any())).thenReturn(booking);

        BookingDtoResponse response = bookingService.changeStatus(item.getOwner().getId(), booking.getId(), false);
        assertNotNull(response);
        assertEquals(response.getStatus(), BookingStatus.REJECTED);
    }

    @Test
    void getById() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        BookingDtoResponse bookingDto = toBookingDto(booking);

        BookingDtoResponse response = bookingService.getById(user.getId(), booking.getId());

        assertNotNull(response);
        assertEquals(bookingDto, response);
    }

    @Test
    void getAllByBooker_ALL() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bookingService.getAllByBooker(1, "ALL", 0, 10);
        verify(bookingRepository).findAllByBookerIdOrderByStartDesc(anyLong(), any());
    }

    @Test
    void getAllByBooker_CURRENT() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bookingService.getAllByBooker(1, "CURRENT", 0, 10);
        verify(bookingRepository).findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(anyLong(), any(), any(), any());
    }

    @Test
    void getAllByBooker_PAST() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bookingService.getAllByBooker(1, "PAST", 0, 10);
        verify(bookingRepository).findAllByBookerIdAndEndBeforeOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void getAllByBooker_FUTURE() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bookingService.getAllByBooker(1, "FUTURE", 0, 10);
        verify(bookingRepository).findAllByBookerIdAndStartAfterOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void getAllByBooker_WAITING() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bookingService.getAllByBooker(1, "WAITING", 0, 10);
        verify(bookingRepository).findAllByBookerIdAndStatusOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void getAllByBooker_REJECTED() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bookingService.getAllByBooker(1, "REJECTED", 0, 10);
        verify(bookingRepository).findAllByBookerIdAndStatusOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void getAllByOwner_ALL() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bookingService.getAllByOwner(1, "ALL", 0, 10);
        verify(bookingRepository).findAllByItemOwnerIdOrderByStartDesc(anyLong(), any());
    }

    @Test
    void getAllByOwner_CURRENT() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bookingService.getAllByOwner(1, "CURRENT", 0, 10);
        verify(bookingRepository).findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(anyLong(), any(), any(), any());
    }

    @Test
    void getAllByOwner_PAST() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bookingService.getAllByOwner(1, "PAST", 0, 10);
        verify(bookingRepository).findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void getAllByOwner_FUTURE() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bookingService.getAllByOwner(1, "FUTURE", 0, 10);
        verify(bookingRepository).findAllByItemOwnerIdAndStartAfterOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void getAllByOwner_WAITING() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bookingService.getAllByOwner(1, "WAITING", 0, 10);
        verify(bookingRepository).findAllByItemOwnerIdAndStatusOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void getAllByOwner_REJECTED() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bookingService.getAllByOwner(1, "REJECTED", 0, 10);
        verify(bookingRepository).findAllByItemOwnerIdAndStatusOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void findFirstByItemIdAndEndBefore() {
        when(bookingRepository.findFirstByItemIdAndEndBeforeOrderByEndDesc(anyLong(), any())).thenReturn(booking);

        assertEquals(booking, bookingService.findFirstByItemIdAndEndBefore(item.getId(), LocalDateTime.now()));
    }

    @Test
    void findFirstByItemIdAndEndAfter() {
        when(bookingRepository.findFirstByItemIdAndEndAfterOrderByStartAsc(anyLong(), any())).thenReturn(booking);

        assertEquals(booking, bookingService.findFirstByItemIdAndEndAfter(item.getId(), LocalDateTime.now()));
    }

    @Test
    void findFirstByItemIdAndBookerIdAndEndBefore() {
        when(bookingRepository.findFirstByItemIdAndBookerIdAndEndBefore(anyLong(), anyLong(), any())).thenReturn(booking);

        assertEquals(booking, bookingService.findFirstByItemIdAndBookerIdAndEndBefore(item.getId(), user.getId(), LocalDateTime.now()));
    }
}