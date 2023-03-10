package ru.practicum.shareIt.booking;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingService {
    BookingDtoResponse addNewBooking(long userId, BookingDto bookingDto);

    BookingDtoResponse changeStatus(long userId, long bookingId, boolean approved);


    BookingDtoResponse getById(long userId, long bookingId);


    Collection<BookingDtoResponse> getAllByBooker(long bookerId, String state);


    Collection<BookingDtoResponse> getAllByOwner(long bookerId, String state);

    Booking findFirstByItemIdAndEndBefore(long ownerId, LocalDateTime end);

    Booking findFirstByItemIdAndEndAfter(long ownerId, LocalDateTime end);

    Booking findFirstByItemIdAndBookerIdAndEndBefore(long itemId, long bookerId, LocalDateTime end);
}
