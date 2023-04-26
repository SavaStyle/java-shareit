package ru.practicum.shareIt.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareIt.booking.Booking;
import ru.practicum.shareIt.booking.BookingService;
import ru.practicum.shareIt.booking.BookingStatus;
import ru.practicum.shareIt.exception.BadRequestException;
import ru.practicum.shareIt.exception.NotFoundException;
import ru.practicum.shareIt.item.comments.Comment;
import ru.practicum.shareIt.item.comments.CommentDto;
import ru.practicum.shareIt.item.comments.CommentRepository;
import ru.practicum.shareIt.user.User;
import ru.practicum.shareIt.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.practicum.shareIt.booking.BookingMapper.toBookingDto;
import static ru.practicum.shareIt.item.ItemMapper.toItemDtoDto;
import static ru.practicum.shareIt.item.comments.CommentMapper.toCommentDto;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    @InjectMocks
    ItemServiceImpl itemService;
    @Mock
    ItemRepository itemRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    BookingService bookingService;
    @Mock
    CommentRepository commentRepository;

    private User user;
    private Item item;
    private ItemDto itemDto;


    @BeforeEach
    void start() {
        user = new User(
                1L,
                "John",
                "john.doe@mail.com");

        item = new Item(
                1L,
                user,
                1L,
                "name",
                "description",
                true);

        itemDto = new ItemDto(
                1L,
                "name",
                "description",
                true);
    }

    @Test
    void getItemsByOwnerId_ok() {
        when(itemRepository.findAllByOwnerId(anyLong(), any())).thenReturn(Collections.emptyList());
        assertTrue(itemService.getItemsByOwnerId(1L, 0, 20).isEmpty());
    }

    @Test
    void getItemsByOwnerId_error() {
        assertThatThrownBy(() -> itemService.getItemsByOwnerId(user.getId(), 0, 0)).isInstanceOf(BadRequestException.class);
    }


    @Test
    void addNewItem_ok() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto itemDto = toItemDtoDto(item);
        ItemDtoResponse actualItem = itemService.addNewItem(user.getId(), itemDto);

        assertNotNull(actualItem);
        assertEquals(itemDto.getName(), actualItem.getName());
        assertEquals(itemDto.getDescription(), actualItem.getDescription());
        assertEquals(itemDto.getAvailable(), actualItem.getAvailable());
    }

    @Test
    void addNewItem_userNotFounded() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> itemService.addNewItem(user.getId(), itemDto)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateItem_ownerNotFounded() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> itemService.updateItem(user.getId(), item.getId(), itemDto)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateItem_ok() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        item.setName("Updated name");
        item.setAvailable(null);

        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto itemDto = toItemDtoDto(item);
        ItemDtoResponse actualItemDto = itemService.updateItem(user.getId(), item.getId(), itemDto);
        assertNotNull(actualItemDto);
        assertEquals(itemDto.getName(), actualItemDto.getName());
        assertEquals(itemDto.getDescription(), actualItemDto.getDescription());
        assertEquals(itemDto.getAvailable(), actualItemDto.getAvailable());
    }

    @Test
    void updateItem_userNotFound() {
        assertThatThrownBy(() -> itemService.updateItem(user.getId(), item.getId(), null)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void deleteItem() {
        itemService.deleteItem(item.getId(), user.getId());

        verify(itemRepository).deleteById(anyLong());
    }

    @Test
    void getItemById_ok() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        ItemDtoResponse actualItemDto = itemService.getItemById(item.getId(), anyLong());

        assertNotNull(actualItemDto);
        assertEquals(itemDto.getName(), actualItemDto.getName());
        assertEquals(itemDto.getDescription(), actualItemDto.getDescription());
        assertEquals(itemDto.getAvailable(), actualItemDto.getAvailable());
    }

    @Test
    void getItemById_userNotFound() {
        assertThatThrownBy(() -> itemService.getItemById(item.getId(), user.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    void search() {
        ItemDtoResponse itemDtoResponse = new ItemDtoResponse(
                1L,
                user,
                1L,
                "name",
                true,
                "description");
        List<Item> items = List.of(item);

        when(itemRepository.search(anyString(), any())).thenReturn(items);

        List<ItemDtoResponse> responses = List.of(itemDtoResponse);

        List<ItemDtoResponse> actualItems = itemService.search("text", 1, 10);

        assertEquals(responses, actualItems);
    }

    @Test
    void setBookings_ok() {
        ItemDtoResponse itemDtoResponse = new ItemDtoResponse(
                1L,
                user,
                1L,
                "name",
                true,
                "description");

        Booking lastBooking = new Booking(
                1L,
                item,
                LocalDateTime.now().minusDays(3),
                LocalDateTime.now().minusDays(2),
                user,
                BookingStatus.WAITING);
        Booking nextBooking = new Booking(
                1L,
                item,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                user,
                BookingStatus.WAITING);
        when(bookingService.findFirstByItemIdAndStartBeforeOrderByEndDesc(anyLong(), any())).thenReturn(lastBooking);
        when(bookingService.findFirstByItemIdAndStartAfterAndStatusIsNotOrderByStartAsc(anyLong(), any())).thenReturn(nextBooking);

        ItemDtoResponse response = itemService.setBookings(itemDtoResponse);
        assertEquals(toBookingDto(lastBooking), response.getLastBooking());
        assertEquals(toBookingDto(nextBooking), response.getNextBooking());
    }


    @Test
    void addComment() {
        Booking booking = new Booking(
                1L,
                item,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(5),
                user,
                BookingStatus.WAITING);
        Comment comment = new Comment(
                1L,
                "text",
                item,
                user,
                LocalDateTime.now());
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(bookingService.findFirstByItemIdAndBookerIdAndEndBefore(anyLong(), anyLong(), any())).thenReturn(booking);
        when(commentRepository.save(any())).thenReturn(comment);

        CommentDto response = itemService.addComment(user.getId(), toCommentDto(comment), item.getId());
        assertEquals(comment.getText(), response.getText());
        assertEquals(comment.getAuthor().getName(), response.getAuthorName());
        assertEquals(comment.getCreated().getMinute(), response.getCreated().getMinute());
    }

    @Test
    void getItemsByRequestId() {
        List<ItemDtoResponse> list = List.of(item)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());

        when(itemRepository.findAllByRequestId(anyLong())).thenReturn(List.of(item));

        List<ItemDtoResponse> responses = itemService.getItemsByRequestId(1L);
        assertEquals(list, responses);
    }
}