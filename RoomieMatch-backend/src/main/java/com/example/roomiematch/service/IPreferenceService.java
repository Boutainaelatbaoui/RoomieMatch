package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.request.PreferenceRequestDTO;
import com.example.roomiematch.model.dto.response.PreferenceResponseDTO;
import com.example.roomiematch.model.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPreferenceService {
    PreferenceResponseDTO createPreference(PreferenceRequestDTO requestDTO);
    List<PreferenceResponseDTO> getAllPreferences();
    Page<PreferenceResponseDTO> getAllPreferences(Pageable pageable);
    PreferenceResponseDTO getPreferenceById(Long id);
    UserResponseDTO getUserByPreference(Long preferenceId);
    PreferenceResponseDTO updatePreference(String userEmail, PreferenceRequestDTO requestDTO);
    void deletePreference(Long id);
}
