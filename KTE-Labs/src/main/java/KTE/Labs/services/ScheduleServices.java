package KTE.Labs.services;

import KTE.Labs.models.Schedule;
import KTE.Labs.models.Ticket;
import KTE.Labs.models.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleServices {


    Schedule createSchedule(LocalDate date, Long docId, LocalTime start, LocalTime end, LocalTime durationOfTreatment);

    List<TimeSlot> getFreeSlotsByDateAndDoctor(LocalDate date, Long docId);

    Ticket makeTicket(LocalDate date, Long docId, Long patientId, LocalTime visitTime);

    List<Ticket> getAllTicketsByPatient(Long patientId);
}
