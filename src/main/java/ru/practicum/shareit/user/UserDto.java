package ru.practicum.shareIt.user;

import lombok.Data;
import ru.practicum.shareIt.user.Interfaces.Create;
import ru.practicum.shareIt.user.Interfaces.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
