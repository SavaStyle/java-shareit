package KTE.Labs.services.Impl;

import KTE.Labs.exceptions.ConflictException;
import KTE.Labs.exceptions.ObjectNotFoundException;
import KTE.Labs.models.*;
import KTE.Labs.repositories.ScheduleRepository;
import KTE.Labs.repositories.TicketRepository;
import KTE.Labs.repositories.TimeSlotRepository;
import KTE.Labs.services.DoctorServices;
import KTE.Labs.services.PatientServices;
import KTE.Labs.services.ScheduleServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServicesImpl implements ScheduleServices {

    private final ScheduleRepository scheduleRepository;
    private final DoctorServices doctorServices;
    private final PatientServices patientServices;
    private final TicketRepository ticketRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public Schedule createSchedule(LocalDate date, Long docId, LocalTime start, LocalTime end, LocalTime durationOfTreatment) {
        Doctor doctor = doctorServices.findById(docId);
        scheduleCheck(date, doctor);

        List<TimeSlot> slots = new ArrayList<>();

        LocalTime timeSlot = start;
        LocalTime lastSlot = end;

        lastSlot = lastSlot.minusHours(durationOfTreatment.getHour());
        lastSlot = lastSlot.minusMinutes(durationOfTreatment.getMinute());

        while (timeSlot.isBefore(lastSlot) || timeSlot.equals(lastSlot)) {

            slots.add(new TimeSlot(timeSlot, true));

            timeSlot = timeSlot.plusHours(durationOfTreatment.getHour());
            timeSlot = timeSlot.plusMinutes(durationOfTreatment.getMinute());
        }

        timeSlotRepository.saveAll(slots);
        Schedule schedule = new Schedule(date, doctor, start, end);

        schedule.setTimeSlot(slots);
        scheduleRepository.save(schedule);
        slots.forEach(slot -> slot.setScheduleId(schedule.getId()));
        return schedule;
    }

    @Override
    public List<TimeSlot> getFreeSlotsByDateAndDoctor(LocalDate date, Long docId) {
        Doctor doctor = doctorServices.findById(docId);
        return timeSlotRepository.findByScheduleIdAndTimeSlot(doctor, date);
    }

    @Override
    public Ticket makeTicket(LocalDate date, Long docId, Long patientId, LocalTime visitTime) {
        Doctor doctor = doctorServices.findById(docId);
        Patient patient = patientServices.findById(patientId);

        TimeSlot timeSlot = timeSlotRepository.findTimeSlotForTicket(doctor, date, visitTime)
                .orElseThrow(() -> new ObjectNotFoundException("Запись на время " + visitTime + " недоступна."));
        timeSlot.setAvailable(false);
        timeSlotRepository.save(timeSlot);

        return ticketRepository.save(new Ticket(doctor, patient, visitTime));
    }

    @Override
    public List<Ticket> getAllTicketsByPatient(Long patientId) {
        Patient patient = patientServices.findById(patientId);
        return ticketRepository.findAllByPatientId(patient);
    }

    private void scheduleCheck(LocalDate date, Doctor doctor) {
        List<Schedule> list = scheduleRepository.findAllByDayOfReceptionAndDoctor(date, doctor);
        if (!list.isEmpty()) {
            throw new ConflictException("Расписание для врача с id " + doctor.getId() + " на дату " + date + " уже создано.");
        }
    }
}
