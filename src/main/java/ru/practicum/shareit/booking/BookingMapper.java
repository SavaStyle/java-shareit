package ru.practicum.shareIt.booking;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareIt.item.Item;
import ru.practicum.shareIt.user.User;

@RequiredArgsConstructor
public class BookingMapper {


    public static Booking fromBookingDto(BookingDto bookingDto, Item item, User user) {
        return new Booking(bookingDto.getId(),
                item,
                bookingDto.getStart(),
                bookingDto.getEnd(),
                user,
                bookingDto.getStatus());
    }

    public static BookingDtoResponse toBookingDto(Booking booking) {
        return new BookingDtoResponse(
                booking.getId(),
                booking.getItem(),
                booking.getStart(),
                booking.getEnd(),
                booking.getBooker(),
                booking.getStatus(),
                booking.getBooker().getId());
    }
}