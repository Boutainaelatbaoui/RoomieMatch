package com.example.roomiematch.model.dto.response;

import com.example.roomiematch.model.entities.City;
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

    public static CityResponseDTO fromEntity(City city) {
        return CityResponseDTO.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }
}

