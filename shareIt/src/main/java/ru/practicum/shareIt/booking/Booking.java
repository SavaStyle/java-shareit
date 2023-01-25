package ru.practicum.shareIt.booking;

import lombok.Data;
import ru.practicum.shareIt.item.Item;
import ru.practicum.shareIt.user.User;

import java.util.Date;

@Data
public class Booking {
    private Long id;
    private Date start;
    private Date end;
    private Item item;
    private User booker;
    private String status;
}
