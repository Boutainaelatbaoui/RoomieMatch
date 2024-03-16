package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.response.RoleResponseDTO;

import java.util.List;

public interface IManagerService {
    void updateUserRole(Integer userId, Long roleId);
    List<RoleResponseDTO> getAllRoles();
}

