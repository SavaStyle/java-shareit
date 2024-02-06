package KTE.Labs.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    Doctor doctorId;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @Nullable
    Patient patientId;

    @Column(name = "visit_time")
    LocalTime visitTime;

    public Ticket(Doctor doctorId, Patient patientId, LocalTime visitTime) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.visitTime = visitTime;
    }
}
