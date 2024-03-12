package com.example.roomiematch.model.entities;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "preferences")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean smoking;
    private boolean pets;
    private boolean visitors;
    private boolean partying;
    private boolean sharingBedroom;
    private boolean hasApartment;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

