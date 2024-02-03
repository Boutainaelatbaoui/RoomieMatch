package com.example.roomiematch.model.entities;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private float rent;
    private int size;
    private int numBedrooms;
    private int numBathrooms;
    private boolean amenities;
    private int floor;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

