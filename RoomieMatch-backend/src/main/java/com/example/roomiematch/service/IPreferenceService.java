package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.request.PreferenceRequestDTO;
import com.example.roomiematch.model.dto.response.PreferenceResponseDTO;

import java.util.List;

public interface IPreferenceService {
    PreferenceResponseDTO createPreference(PreferenceRequestDTO requestDTO);
    List<PreferenceResponseDTO> getAllPreferences();
    PreferenceResponseDTO getPreferenceById(Long id);
    PreferenceResponseDTO updatePreference(Long id, PreferenceRequestDTO requestDTO);
    void deletePreference(Long id);
}
