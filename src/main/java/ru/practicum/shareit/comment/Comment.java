package ru.practicum.shareit.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created")
    private LocalDateTime created;
}
