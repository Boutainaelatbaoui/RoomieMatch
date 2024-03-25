package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.request.PreferenceRequestDTO;
import com.example.roomiematch.model.dto.response.PreferenceResponseDTO;
import com.example.roomiematch.model.dto.response.UserResponseDTO;
import com.example.roomiematch.service.IPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final IPreferenceService preferenceService;

    @GetMapping
    public ResponseEntity<List<PreferenceResponseDTO>> getAllPreferences() {
        return ResponseEntity.ok(preferenceService.getAllPreferences());
    }

    @PostMapping
    public ResponseEntity<PreferenceResponseDTO> createPreference(@Valid @RequestBody PreferenceRequestDTO requestDTO) {
        PreferenceResponseDTO responseDTO = preferenceService.createPreference(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreferenceResponseDTO> getPreferenceById(@PathVariable Long id) {
        PreferenceResponseDTO responseDTO = preferenceService.getPreferenceById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{preferenceId}/users")
    public ResponseEntity<UserResponseDTO> getUsersByPreference(@PathVariable Long preferenceId) {
        UserResponseDTO user = preferenceService.getUserByPreference(preferenceId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userEmail}")
    public ResponseEntity<PreferenceResponseDTO> updatePreference(@PathVariable String userEmail, @Valid @RequestBody PreferenceRequestDTO requestDTO) {
        PreferenceResponseDTO responseDTO = preferenceService.updatePreference(userEmail, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<PreferenceResponseDTO>> getAllPreferencesPaged(Pageable pageable) {
        return ResponseEntity.ok(preferenceService.getAllPreferences(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreference(@PathVariable Long id) {
        preferenceService.deletePreference(id);
        return ResponseEntity.status(200).build();
    }
}

