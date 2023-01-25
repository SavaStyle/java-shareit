package ru.practicum.shareIt.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareIt.user.Interfaces.Create;
import ru.practicum.shareIt.user.Interfaces.Update;

@Data
public class UserDto {

    private Long id;
    @NotBlank(groups = {Create.class})
    private String name;
    @NotNull(groups = {Create.class})
    @Email(groups = {Create.class, Update.class})
    private String email;

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
