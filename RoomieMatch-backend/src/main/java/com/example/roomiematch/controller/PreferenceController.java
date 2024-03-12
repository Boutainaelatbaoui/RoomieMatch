package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.request.PreferenceRequestDTO;
import com.example.roomiematch.model.dto.response.PreferenceResponseDTO;
import com.example.roomiematch.service.IPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PutMapping("/{id}")
    public ResponseEntity<PreferenceResponseDTO> updatePreference(@PathVariable Long id, @Valid @RequestBody PreferenceRequestDTO requestDTO) {
        PreferenceResponseDTO responseDTO = preferenceService.updatePreference(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreference(@PathVariable Long id) {
        preferenceService.deletePreference(id);
        return ResponseEntity.status(200).build();
    }
}

