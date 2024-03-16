package com.example.roomiematch.model.dto.response;

import com.example.roomiematch.model.entities.City;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String telephone;
    private String bio;
    private double budget;
    private int occupation;
    private int gender;
    private String email;
    private String birthdate;
    private String profilePicture;
    private CityResponseDTO currentCity;
    private CityResponseDTO desiredCity;
    private PreferenceResponseDTO preference;
    private RoleResponseDTO role;
}

