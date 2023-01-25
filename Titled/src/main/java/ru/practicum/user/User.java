package ru.practicum.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class User {
    private Long id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }
}