package ru.practicum.shareIt.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareIt.booking.BookingService;
import ru.practicum.shareIt.item.comments.CommentRepository;
import ru.practicum.shareIt.user.UserRepository;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    @InjectMocks
    ItemServiceImpl itemService;
    @Mock
    ItemRepository repository;
    @Mock
    UserRepository userRepository;
    @Mock
    BookingService bookingService;
    @Mock
    CommentRepository commentRepository;


    @Test
    void getItemsByOwnerId() {
    }

    @Test
    void addNewItem() {
    }

    @Test
    void updateItem() {
    }

    @Test
    void deleteItem() {
    }

    @Test
    void getItemById() {
    }

    @Test
    void search() {
    }

    @Test
    void setBookings() {
    }

    @Test
    void setComments() {
    }

    @Test
    void addComment() {
    }

    @Test
    void getItemsByRequestId() {
    }
}