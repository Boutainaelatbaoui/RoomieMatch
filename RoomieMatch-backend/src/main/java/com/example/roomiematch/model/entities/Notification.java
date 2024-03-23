package com.example.roomiematch.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    private String message;

    private boolean isRead;
}
