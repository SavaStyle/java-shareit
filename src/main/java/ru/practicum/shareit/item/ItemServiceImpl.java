package ru.practicum.shareIt.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareIt.booking.Booking;
import ru.practicum.shareIt.booking.BookingRepository;
import ru.practicum.shareIt.booking.BookingService;
import ru.practicum.shareIt.exception.BadRequestException;
import ru.practicum.shareIt.exception.NotFoundException;
import ru.practicum.shareIt.item.comments.Comment;
import ru.practicum.shareIt.item.comments.CommentDto;
import ru.practicum.shareIt.item.comments.CommentMapper;
import ru.practicum.shareIt.item.comments.CommentRepository;
import ru.practicum.shareIt.user.User;
import ru.practicum.shareIt.user.UserRepository;
import ru.practicum.shareIt.user.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareIt.booking.BookingMapper.toBookingDto;
import static ru.practicum.shareIt.item.comments.CommentMapper.fromCommentDto;
import static ru.practicum.shareIt.item.comments.CommentMapper.toCommentDto;
import static ru.practicum.shareIt.item.ItemMapper.fromItemDto;
import static ru.practicum.shareIt.item.ItemMapper.toItemDto;

@Service
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;
    private final BookingService bookingService;
    private final CommentRepository commentRepository;

    @Override
    public List<ItemDtoResponse> getItemsByOwnerId(long userId) {
        return repository
                .findAllByOwnerId(userId)
                .stream()
                .map(ItemMapper::toItemDto)
                .map(this::setBookings)
                .map(this::setComments)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDtoResponse addNewItem(long userId, ItemDto itemDto) {
        User owner = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("Owner не найден");
        });
        Item item = fromItemDto(itemDto);
        item.setOwner(owner);
        repository.save(item);
        return toItemDto(item);
    }

    @Override
    @Transactional
    public ItemDtoResponse updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item item = repository.findById(itemId).orElseThrow(() -> {
            throw new NotFoundException("Item не найден");
        });
        User owner = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("Owner не найден");
        });
        if (!item.getOwner().equals(owner)) {
            throw new NotFoundException("Вещь вам не пренадлежит");
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        repository.save(item);
        return toItemDto(item);
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        repository.deleteById(itemId);
    }

    @Override
    public ItemDtoResponse getItemById(long itemId, long userId) {
        ItemDtoResponse itemDtoResponse = toItemDto(repository.findById(itemId).orElseThrow(() -> {
            throw new NotFoundException("Item не найден");
        }));
        if (itemDtoResponse.getOwner().getId().equals(userId)) {
            setBookings(itemDtoResponse);
        }
        setComments(itemDtoResponse);
        return itemDtoResponse;
    }

    @Override
    public List<ItemDtoResponse> search(String text) {
        if (text.isBlank())
            return Collections.emptyList();
        return repository
                .search(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public ItemDtoResponse setBookings(ItemDtoResponse itemDtoResponse) {
        Booking lastBooking = bookingService.findFirstByItemIdAndEndBefore(itemDtoResponse.getId(), LocalDateTime.now());
        Booking nextBooking = bookingService.findFirstByItemIdAndEndAfter(itemDtoResponse.getId(), LocalDateTime.now());
        if (lastBooking != null) {
            itemDtoResponse.setLastBooking(toBookingDto(lastBooking));
        }
        if (nextBooking != null) {
            itemDtoResponse.setNextBooking(toBookingDto(nextBooking));
        }
        return itemDtoResponse;
    }

    public ItemDtoResponse setComments(ItemDtoResponse itemDtoResponse) {
        Collection<Comment> commitCollection = commentRepository.findByItemId(itemDtoResponse.getId());
        List<CommentDto> commitList = commitCollection.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
        itemDtoResponse.setComments(commitList);
        return itemDtoResponse;
    }

    @Override
    public CommentDto addComment(long userId, CommentDto commentDto, long itemId) {
        Item item = repository.findById(itemId).orElseThrow(() -> {
            throw new NotFoundException("Объект не найден");
        });
        User autor = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("Комментатор не найден");
        });
        Booking booking = bookingService.findFirstByItemIdAndBookerIdAndEndBefore(itemId, userId, LocalDateTime.now());
        if (booking == null) {
            throw new BadRequestException("вы не можете добавить отзыв");
        }
        Comment comment = fromCommentDto(commentDto);
        comment.setItem(item);
        comment.setAuthor(autor);
        comment.setCreated(LocalDateTime.now());
        commentRepository.save(comment);
        return toCommentDto(comment);
    }
}