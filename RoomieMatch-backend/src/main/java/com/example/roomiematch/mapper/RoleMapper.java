package com.example.roomiematch.mapper;

import com.example.roomiematch.model.dto.response.RoleResponseDTO;
import com.example.roomiematch.model.entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleResponseDTO toResponseDTO(Role role) {
        return RoleResponseDTO.builder()
                .id(role.getId())
                .name(role.getName().toString())
                .build();
    }
}
