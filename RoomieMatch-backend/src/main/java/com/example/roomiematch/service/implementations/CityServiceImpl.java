package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.CityMapper;
import com.example.roomiematch.model.dto.response.CityResponseDTO;
import com.example.roomiematch.model.entities.City;
import com.example.roomiematch.repository.CityRepository;
import com.example.roomiematch.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements ICityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public List<CityResponseDTO> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cities.stream()
                .map(cityMapper::toCityResponseDTO)
                .collect(Collectors.toList());
    }
}

