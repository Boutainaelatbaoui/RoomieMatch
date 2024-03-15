package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.response.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserResponseDTO> getAllUsers();
    Optional<UserResponseDTO> getUserById(Long userId);
}
