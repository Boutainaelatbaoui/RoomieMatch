package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.RoleMapper;
import com.example.roomiematch.model.dto.response.RoleResponseDTO;
import com.example.roomiematch.model.entities.Role;
import com.example.roomiematch.repository.RoleRepository;
import com.example.roomiematch.repository.UserRepository;
import com.example.roomiematch.service.IManagerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class managerServiceImpl implements IManagerService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public void updateUserRole(Integer userId, Long roleId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        var newRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        user.setRole(newRole);
        userRepository.save(user);
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponseDTO)
                .toList();
    }
}
