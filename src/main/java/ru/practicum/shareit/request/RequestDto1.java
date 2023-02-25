package ru.practicum.shareIt.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareIt.user.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto1 {

    private Long id;
    @NotNull
    private String description;
    private User requestor;
    private LocalDateTime created;
}
