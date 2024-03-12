package com.example.roomiematch.mapper;

import com.example.roomiematch.model.dto.request.PreferenceRequestDTO;
import com.example.roomiematch.model.dto.response.PreferenceResponseDTO;
import com.example.roomiematch.model.entities.Preference;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PreferenceMapper {
    private final UserRepository userRepository;

    @Autowired
    public PreferenceMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Preference toEntity(PreferenceRequestDTO preferenceRequestDTO) {
        User user = userRepository.findById(preferenceRequestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return Preference.builder()
                .smoking(preferenceRequestDTO.isSmoking())
                .pets(preferenceRequestDTO.isPets())
                .visitors(preferenceRequestDTO.isVisitors())
                .partying(preferenceRequestDTO.isPartying())
                .sharingBedroom(preferenceRequestDTO.isSharingBedroom())
                .hasApartment(preferenceRequestDTO.isHasApartment())
                .user(user)
                .build();
    }
    public PreferenceResponseDTO toDTO(Preference preference) {
        return PreferenceResponseDTO.builder()
                .id(preference.getId())
                .smoking(preference.isSmoking())
                .pets(preference.isPets())
                .visitors(preference.isVisitors())
                .partying(preference.isPartying())
                .sharingBedroom(preference.isSharingBedroom())
                .hasApartment(preference.isHasApartment())
                .build();
    }

    public PreferenceRequestDTO toRequestDTO(Preference preference) {
        return PreferenceRequestDTO.builder()
                .smoking(preference.isSmoking())
                .pets(preference.isPets())
                .visitors(preference.isVisitors())
                .partying(preference.isPartying())
                .sharingBedroom(preference.isSharingBedroom())
                .hasApartment(preference.isHasApartment())
                .userId(preference.getUser().getId())
                .build();
    }

    public List<PreferenceResponseDTO> toDTOList(List<Preference> preferences) {
        return preferences.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

