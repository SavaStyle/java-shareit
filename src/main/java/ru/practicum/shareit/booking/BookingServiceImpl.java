package ru.practicum.shareIt.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareIt.exception.BadRequestException;
import ru.practicum.shareIt.exception.NotFoundException;
import ru.practicum.shareIt.exception.StatusException;
import ru.practicum.shareIt.item.Item;
import ru.practicum.shareIt.item.ItemRepository;
import ru.practicum.shareIt.user.User;
import ru.practicum.shareIt.user.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.practicum.shareIt.booking.BookingMapper.fromBookingDto;
import static ru.practicum.shareIt.booking.BookingMapper.toBookingDto;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BookingDtoResponse addNewBooking(long userId, BookingDto bookingDto) {
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow(() -> new NotFoundException("Обект не найден"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (item.getOwner().getId() == (userId)) {
            throw new NotFoundException("нельзя забронировать собственную вещь");
        }
        if (!item.getAvailable()) {
            throw new BadRequestException("вещь недоступна");
        }
        if (bookingDto.getStart().isBefore(LocalDateTime.now()) ||
                bookingDto.getEnd().isBefore(bookingDto.getStart()) ||
                bookingDto.getEnd().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("сроки бронирвания не верны");
        }
        Booking booking = fromBookingDto(bookingDto, item, user);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);
        return toBookingDto(booking);
    }

    @Override
    @Transactional
    public BookingDtoResponse changeStatus(long userId, long bookingId, boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Бронирование не найдено"));
        Item item = booking.getItem();
        if (item.getOwner().getId() != userId) {
            throw new NotFoundException("вы не владелец вещи");
        }
        if (booking.getStatus() != BookingStatus.WAITING) {
            throw new BadRequestException("данный статус нельзя изменить");
        }
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        bookingRepository.save(booking);
        return toBookingDto(booking);
    }

    @Override
    @Transactional
    public BookingDtoResponse getById(long userId, long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Бронирование не найдено"));
        Item item = booking.getItem();
        if (booking.getBooker().getId() != userId && item.getOwner().getId() != userId) {
            throw new NotFoundException("Действие не возможно");
        }
        return toBookingDto(booking);
    }

    @Override
    @Transactional
    public Collection<BookingDtoResponse> getAllByBooker(long bookerId, String state, int from, int size) {
        Collection<Booking> bookingList;
        User user = userRepository.findById(bookerId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (size <= 0 || from < 0) {
            throw new BadRequestException("параметры запроса страниц не верны");
        }
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);
        switch (state) {
            case "ALL":
                bookingList = bookingRepository.findAllByBookerIdOrderByStartDesc(bookerId, pageRequest);
                break;
            case "CURRENT":
                bookingList = bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(bookerId, LocalDateTime.now(), LocalDateTime.now(), pageRequest);
                break;
            case "PAST":
                bookingList = bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(bookerId, LocalDateTime.now(), pageRequest);
                break;
            case "FUTURE":
                bookingList = bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(bookerId, LocalDateTime.now(), pageRequest);
                break;
            case "WAITING":
                bookingList = bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(bookerId, BookingStatus.WAITING, pageRequest);
                break;
            case "REJECTED":
                bookingList = bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(bookerId, BookingStatus.REJECTED, pageRequest);
                break;
            default:
                throw new StatusException();
        }
        return bookingList.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Collection<BookingDtoResponse> getAllByOwner(long bookerId, String state, int from, int size) {
        Collection<Booking> bookingList;
        User user = userRepository.findById(bookerId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (size <= 0 || from < 0) {
            throw new BadRequestException("параметры запроса страниц не верны");
        }
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);
        switch (state) {
            case "ALL":
                bookingList = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(bookerId, pageRequest);
                break;
            case "CURRENT":
                bookingList = bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(bookerId, LocalDateTime.now(), LocalDateTime.now(), pageRequest);
                break;
            case "PAST":
                bookingList = bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(bookerId, LocalDateTime.now(), pageRequest);
                break;
            case "FUTURE":
                bookingList = bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(bookerId, LocalDateTime.now(), pageRequest);
                break;
            case "WAITING":
                bookingList = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(bookerId, BookingStatus.WAITING, pageRequest);
                break;
            case "REJECTED":
                bookingList = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(bookerId, BookingStatus.REJECTED, pageRequest);
                break;
            default:
                throw new StatusException();
        }
        return bookingList.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public Booking findFirstByItemIdAndEndBefore(long itemId, LocalDateTime end) {
        return bookingRepository.findFirstByItemIdAndEndBeforeOrderByEndDesc(itemId, end);
    }

    @Override
    public Booking findFirstByItemIdAndEndAfter(long itemId, LocalDateTime end) {
        return bookingRepository.findFirstByItemIdAndEndAfterOrderByStartAsc(itemId, end);
    }

    @Override
    public Booking findFirstByItemIdAndBookerIdAndEndBefore(long itemId, long bookerId, LocalDateTime end) {
        return bookingRepository.findFirstByItemIdAndBookerIdAndEndBefore(itemId, bookerId, end);
    }
}
