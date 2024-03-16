package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.response.RoleResponseDTO;
import com.example.roomiematch.model.entities.Role;
import com.example.roomiematch.service.IManagerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
public class ManagementController {
    private final IManagerService managerService;

    @PutMapping("/updateRole/{userId}/{roleId}")
//    @PreAuthorize("hasAuthority('CAN_MANAGE_USERS')")
    public ResponseEntity<Void> updateUserRole(@PathVariable Integer userId, @PathVariable Long roleId){
        managerService.updateUserRole(userId, roleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/roles")
//    @PreAuthorize("hasAuthority('CAN_MANAGE_USERS')")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles(){
        return ResponseEntity.ok(managerService.getAllRoles());
    }
}

