package KTE.Labs.repositories;

import KTE.Labs.models.Patient;
import KTE.Labs.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByPatientId(Patient patient);
}
