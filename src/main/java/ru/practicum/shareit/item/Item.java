package ru.practicum.shareIt.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareIt.user.User;

import javax.persistence.*;

@Data
@Table(name = "items")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @Column(name = "request_id", length = 100)
    private Long requestId;
    @Column(name = "item_name", length = 100, nullable = false)
    private String name;
    @Column(name = "description", length = 100, nullable = false)
    private String description;
    @Column(name = "is_available", nullable = false)
    private Boolean available;


    public Item(Long requestId, String name, String description, Boolean available) {
        this.requestId = requestId;
        this.name = name;
        this.description = description;
        this.available = available;
    }
}