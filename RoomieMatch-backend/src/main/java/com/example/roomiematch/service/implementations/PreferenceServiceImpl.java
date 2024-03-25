package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.PreferenceMapper;
import com.example.roomiematch.model.dto.request.PreferenceRequestDTO;
import com.example.roomiematch.model.dto.response.PreferenceResponseDTO;
import com.example.roomiematch.model.entities.Preference;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.PreferenceRepository;
import com.example.roomiematch.repository.UserRepository;
import com.example.roomiematch.service.IPreferenceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements IPreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final PreferenceMapper preferenceMapper;
    private final UserRepository userRepository;

    @Override
    public PreferenceResponseDTO createPreference(PreferenceRequestDTO requestDTO) {
        Preference preference = preferenceRepository.findByUserId(requestDTO.getUserId())
                .orElse(null);

        if (preference != null) {
            preference.setSmoking(requestDTO.isSmoking());
            preference.setPets(requestDTO.isPets());
            preference.setVisitors(requestDTO.isVisitors());
            preference.setPartying(requestDTO.isPartying());
            preference.setSharingBedroom(requestDTO.isSharingBedroom());
            preference.setHasApartment(requestDTO.isHasApartment());
        } else {
            preference = preferenceMapper.toEntity(requestDTO);
        }

        preference = preferenceRepository.save(preference);

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + requestDTO.getUserId()));

        user.setPreference(preference);

        userRepository.save(user);

        return preferenceMapper.toDTO(preference);
    }


    @Override
    public PreferenceResponseDTO getPreferenceById(Long id) {
        Preference preference = preferenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Preference not found"));
        return preferenceMapper.toDTO(preference);
    }

    @Override
    public PreferenceResponseDTO updatePreference(String userEmail, PreferenceRequestDTO requestDTO) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));

        Preference preference = preferenceRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Preference not found for user with email: " + userEmail));

        preference.setSmoking(requestDTO.isSmoking());
        preference.setPets(requestDTO.isPets());
        preference.setVisitors(requestDTO.isVisitors());
        preference.setPartying(requestDTO.isPartying());
        preference.setSharingBedroom(requestDTO.isSharingBedroom());
        preference.setHasApartment(requestDTO.isHasApartment());

        Preference updatedPreference = preferenceRepository.save(preference);
        return preferenceMapper.toDTO(updatedPreference);
    }

    @Override
    public void deletePreference(Long id) {
        if(!preferenceRepository.existsById(id)) {
            throw new EntityNotFoundException("Preference not found");
        } else {
            preferenceRepository.deleteById(id);
        }
    }

    @Override
    public List<PreferenceResponseDTO> getAllPreferences() {
        List<Preference> preferences = preferenceRepository.findAll();
        return preferences.stream()
                .map(preferenceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PreferenceResponseDTO> getAllPreferences(Pageable pageable) {
        Page<Preference> preferences = preferenceRepository.findAll(pageable);
        return preferences.map(preferenceMapper::toDTO);
    }
}
