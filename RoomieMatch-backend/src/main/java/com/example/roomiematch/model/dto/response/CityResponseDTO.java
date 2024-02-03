package com.example.roomiematch.model.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityResponseDTO {
    private Long id;
    private String name;
//    private List<UserResponseDTO> usersInCurrentCity;
//    private List<UserResponseDTO> usersInDesiredCity;
}

