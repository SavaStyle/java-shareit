package KTE.Labs.controllers;

import KTE.Labs.models.Schedule;
import KTE.Labs.models.Ticket;
import KTE.Labs.models.TimeSlot;
import KTE.Labs.services.ScheduleServices;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/schedule")
public class ScheduleController {

    private final ScheduleServices scheduleServices;

    @GetMapping("/createSchedule")
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule createSchedule(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam LocalDate date,
                                   @RequestParam Long docId,
                                   @DateTimeFormat(pattern = "HH:mm:ss") @RequestParam LocalTime start,
                                   @DateTimeFormat(pattern = "HH:mm:ss") @RequestParam LocalTime end,
                                   @DateTimeFormat(pattern = "HH:mm:ss") @RequestParam LocalTime durationOfTreatment) {
        return scheduleServices.createSchedule(date, docId, start, end, durationOfTreatment);
    }

    @GetMapping("/freeSlots")
    @ResponseStatus(HttpStatus.CREATED)
    public List<TimeSlot> getFreeSlotsByDateAndDoctor(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam LocalDate date,
                                                      @RequestParam Long docId) {
        return scheduleServices.getFreeSlotsByDateAndDoctor(date, docId);
    }

    @GetMapping("/makeTicket")
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket makeTicket(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam LocalDate date,
                             @RequestParam Long docId,
                             @RequestParam Long patientId,
                             @DateTimeFormat(pattern = "HH:mm:ss") @RequestParam LocalTime visitTime) {
        return scheduleServices.makeTicket(date, docId, patientId, visitTime);
    }

    @GetMapping("/ticketsByPatient/{patientId}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Ticket> getAllTicketsByPatient(@PathVariable Long patientId) {
        return scheduleServices.getAllTicketsByPatient(patientId);
    }
}
