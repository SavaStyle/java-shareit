package ru.practicum.shareIt.item.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareIt.user.Interfaces.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CommentDto {
    private Long id;
    @NotNull(groups = Create.class)
    @NotBlank(groups = Create.class)
    private String text;
    private String authorName;
    private LocalDateTime created;
}