package ru.practicum.shareIt.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> getAllByRequestorId(Long requestorId);

    List<Request> findAllByRequestorIdIsNotOrderByCreatedDesc(long requestorId, PageRequest pageable);
}
