package com.example.roomiematch.mapper;

import com.example.roomiematch.model.dto.response.CityResponseDTO;
import com.example.roomiematch.model.entities.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public CityResponseDTO toCityResponseDTO(City city) {
        return CityResponseDTO.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }
}

