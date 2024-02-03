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
    private int cleanliness;
    private int noiseTolerance;
    private int socialLevel;
    private String others;

    @OneToOne(mappedBy = "preference")
    private User user;
}

