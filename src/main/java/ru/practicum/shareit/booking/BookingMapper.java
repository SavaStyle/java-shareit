package ru.practicum.shareIt.booking;

import lombok.experimental.UtilityClass;
import ru.practicum.shareIt.item.Item;
import ru.practicum.shareIt.user.User;


@UtilityClass
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

    public static BookingDto toBookingDtoDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getBooker().getId(),
                booking.getStatus());
    }


    public static BookingDtoResponse toBookingDtoTest(BookingDto bookingDto, Item item, User user) {
        return new BookingDtoResponse(
                bookingDto.getId(),
                item,
                bookingDto.getStart(),
                bookingDto.getEnd(),
                user,
                bookingDto.getStatus(),
                user.getId());
    }
}