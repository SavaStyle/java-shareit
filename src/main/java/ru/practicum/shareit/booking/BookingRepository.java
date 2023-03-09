package ru.practicum.shareIt.booking;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerIdOrderByStartDesc(long bookerId, PageRequest pageable);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long bookerId, LocalDateTime start, LocalDateTime end, PageRequest pageable);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(long bookerId, LocalDateTime end, PageRequest pageable);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(long bookerId, LocalDateTime end, PageRequest pageable);

    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(long bookerId, BookingStatus status, PageRequest pageable);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(long ownerId, PageRequest pageable);

    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long ownerId, LocalDateTime start, LocalDateTime end, PageRequest pageable);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(long ownerId, LocalDateTime end, PageRequest pageable);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(long ownerId, LocalDateTime end, PageRequest pageable);

    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(long ownerId, BookingStatus status, PageRequest pageable);

    Booking findFirstByItemIdAndEndBeforeOrderByEndDesc(long itemId, LocalDateTime end);

    Booking findFirstByItemIdAndEndAfterAndStatusIsNotOrderByStartAsc(Long item_id, LocalDateTime end, BookingStatus status);

    Booking findFirstByItemIdAndBookerIdAndEndBefore(long itemId, long bookerId, LocalDateTime end);
}
