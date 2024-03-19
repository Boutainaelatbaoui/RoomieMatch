package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.response.UserResponseDTO;

import java.util.List;

public interface IRoommateMatcherService {
    List<UserResponseDTO> findRoommatesForUserByEmail(String userEmail);
}

