package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.response.CityResponseDTO;
import com.example.roomiematch.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {

    private final ICityService cityService;

    @Autowired
    public CityController(ICityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<CityResponseDTO> getAllCities() {
        return cityService.getAllCities();
    }
}

