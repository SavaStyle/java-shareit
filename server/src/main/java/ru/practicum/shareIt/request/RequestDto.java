package ru.practicum.shareIt.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {

    private Long id;
    @NotNull
    private String description;
    private Long requestorId;
    private LocalDateTime created;
}
