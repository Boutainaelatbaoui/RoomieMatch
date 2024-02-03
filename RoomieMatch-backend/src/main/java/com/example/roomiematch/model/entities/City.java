package com.example.roomiematch.model.entities;

import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Getter
@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "currentCity")
    private List<User> usersInCurrentCity;

    @OneToMany(mappedBy = "desiredCity")
    private List<User> usersInDesiredCity;
}

