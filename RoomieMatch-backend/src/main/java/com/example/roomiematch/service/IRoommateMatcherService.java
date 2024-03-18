package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.response.RoommateMatchDTO;

import java.util.List;

public interface IRoommateMatcherService {
    List<RoommateMatchDTO> findRoommatesForUserByEmail(String userEmail);
}

