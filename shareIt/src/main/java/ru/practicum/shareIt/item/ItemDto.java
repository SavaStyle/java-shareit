package ru.practicum.shareIt.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareIt.user.Interfaces.Create;

@Data
public class ItemDto {
    private Long id;
    private Long ownerId;
    private Long request;
    @NotBlank(groups = Create.class)
    private String name;
    @NotBlank(groups = Create.class)
    private String description;
    @NotNull(groups = Create.class)
    private Boolean available;
}
