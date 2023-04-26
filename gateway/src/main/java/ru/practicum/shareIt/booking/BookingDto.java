package ru.practicum.shareIt.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareIt.user.Interfaces.Create;
import ru.practicum.shareIt.user.Interfaces.Update;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDto {
    @NotNull(groups = Update.class)
    private Long id;
    @NotNull(groups = Create.class)
    @FutureOrPresent(groups = Create.class)
    private LocalDateTime start;
    @NotNull(groups = Create.class)
    @Future(groups = Create.class)
    private LocalDateTime end;
    @NotNull(groups = Create.class)
    private Long itemId;
    private Long bookerId;
    private BookingStatus status;
}
