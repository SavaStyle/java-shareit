package KTE.Labs.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "time_slot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_slot_id")
    Long id;

    @NotNull
    @Column(name = "time_slot_start")
    LocalTime timeSlot;

    @NotNull
    @Column(name = "available")
    boolean available;

    @Column(name = "schedule_id")
    Long scheduleId;

    public TimeSlot(LocalTime timeSlot, boolean available) {
        this.timeSlot = timeSlot;
        this.available = available;
    }
}
