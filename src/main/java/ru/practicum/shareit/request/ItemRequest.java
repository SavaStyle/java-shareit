package ru.practicum.shareIt.request;

import lombok.Data;
import ru.practicum.shareIt.user.User;

import java.util.Date;

@Data
public class ItemRequest {
    private Long id;
    private String description;
    private User requestor;
    private Date created;
}
