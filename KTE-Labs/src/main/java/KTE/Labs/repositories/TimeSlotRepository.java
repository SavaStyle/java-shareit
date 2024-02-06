package KTE.Labs.repositories;

import KTE.Labs.models.Doctor;
import KTE.Labs.models.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    @Query("select ts from TimeSlot ts " +
            "left join Schedule s on s.id=ts.scheduleId " +
            "where s.doctor = ?1 and s.dayOfReception = ?2 and ts.available = true")
    List<TimeSlot> findByScheduleIdAndTimeSlot(Doctor doctor, LocalDate date);

    @Query("select ts from TimeSlot ts " +
            "left join Schedule s on s.id=ts.scheduleId " +
            "where s.doctor = ?1 and s.dayOfReception = ?2 and ts.timeSlot = ?3 and ts.available = true")
    Optional<TimeSlot> findTimeSlotForTicket(Doctor doctor, LocalDate date, LocalTime time);
}
