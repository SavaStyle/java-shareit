package KTE.Labs.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    Long id;

    @Column(name = "patient_name", nullable = false, length = 256)
    String name;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;
}
