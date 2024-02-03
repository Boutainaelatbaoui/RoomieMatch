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

    private boolean isActive;

    private City city;
}

