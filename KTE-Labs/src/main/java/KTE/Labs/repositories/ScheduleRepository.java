package KTE.Labs.repositories;

import KTE.Labs.models.Doctor;
import KTE.Labs.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByDayOfReceptionAndDoctor(LocalDate date, Doctor doctor);
}
