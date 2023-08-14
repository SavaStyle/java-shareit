package ru.practicum.shareIt.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareIt.item.Item;
import ru.practicum.shareIt.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    @Column(name = "start_time", nullable = false)
    private LocalDateTime start;
    @Column(name = "end_time", nullable = false)
    private LocalDateTime end;
    @ManyToOne
    @JoinColumn(name = "booker_id", nullable = false)
    private User booker;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
