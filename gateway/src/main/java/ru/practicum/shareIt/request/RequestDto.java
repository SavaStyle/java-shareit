package ru.practicum.shareIt.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareIt.user.Interfaces.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {

    private Long id;
    @NotNull(groups = Create.class)
    @NotBlank(groups = Create.class)
    private String description;
    private Long requestorId;
    private LocalDateTime created;
}
