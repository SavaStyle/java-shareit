package ru.practicum.shareIt.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerIdOrderByStartDesc(long bookerId);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long bookerId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(long bookerId, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(long bookerId, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(long bookerId, BookingStatus status);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(long ownerId);

    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long ownerId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(long ownerId, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(long ownerId, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(long ownerId, BookingStatus status);

    Booking findFirstByItemIdAndEndBeforeOrderByEndDesc(long itemId, LocalDateTime end);

    Booking findFirstByItemIdAndEndAfterOrderByStartAsc(long itemId, LocalDateTime end);

    Booking findFirstByItemIdAndBookerIdAndEndBefore(long itemId, long bookerId, LocalDateTime end);
}
