package KTE.Labs.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    Long id;

    @Column(name = "day_of_reception")
    @Future
    @NotNull
    LocalDate dayOfReception;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "doctor_id")
    Doctor doctor;

    @NotNull
    @Column(name = "start_of_reception")
    LocalTime startOfReception;

    @NotNull
    @Column(name = "end_of_reception")
    LocalTime endOfReception;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    List<TimeSlot> timeSlot = new ArrayList<>();

    public Schedule(LocalDate dayOfReception, Doctor doctor, LocalTime startOfReception, LocalTime endOfReception) {
        this.dayOfReception = dayOfReception;
        this.doctor = doctor;
        this.startOfReception = startOfReception;
        this.endOfReception = endOfReception;
    }
}
