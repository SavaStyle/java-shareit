package ru.practicum.shareIt.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareIt.user.Interfaces.Create;
import ru.practicum.shareIt.user.Interfaces.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(groups = {Create.class})
    private String name;
    @NotNull(groups = {Create.class})
    @Email(groups = {Create.class, Update.class})
    private String email;
}
