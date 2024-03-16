package com.example.roomiematch.mapper;

import com.example.roomiematch.model.dto.response.RoleResponseDTO;
import com.example.roomiematch.model.dto.response.UserResponseDTO;
import com.example.roomiematch.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PreferenceMapper preferenceMapper;
    private final CityMapper cityMapper;
    private final RoleMapper roleMapper;

    @Autowired
    public UserMapper(PreferenceMapper preferenceMapper, CityMapper cityMapper, RoleMapper roleMapper) {
        this.preferenceMapper = preferenceMapper;
        this.cityMapper = cityMapper;
        this.roleMapper = roleMapper;
    }

    public UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .telephone(user.getTelephone())
                .bio(user.getBio())
                .budget(user.getBudget())
                .occupation(user.getOccupation())
                .gender(user.getGender())
                .birthdate(user.getBirthdate().toString())
                .profilePicture(user.getProfilePicture())
                .email(user.getEmail())
                .currentCity(cityMapper.toCityResponseDTO(user.getCurrentCity()))
                .desiredCity(cityMapper.toCityResponseDTO(user.getDesiredCity()))
                .preference(preferenceMapper.toDTO(user.getPreference()))
                .role(roleMapper.toResponseDTO(user.getRole()))
                .build();
    }
}
