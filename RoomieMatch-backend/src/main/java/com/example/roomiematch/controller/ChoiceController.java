package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.request.ChoiceRequestDTO;
import com.example.roomiematch.model.dto.response.ChoiceResponseDTO;
import com.example.roomiematch.service.IChoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/choices")
public class ChoiceController {

    private final IChoiceService choiceService;

    @Autowired
    public ChoiceController(IChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @GetMapping
    public List<ChoiceResponseDTO> getAllChoices() {
        return choiceService.getAllChoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChoiceResponseDTO> getChoiceById(@PathVariable Long id) {
        ChoiceResponseDTO choice = choiceService.getChoiceById(id);
        return ResponseEntity.ok(choice);
    }

    @PostMapping
    public ResponseEntity<ChoiceResponseDTO> createChoice(@Valid @RequestBody ChoiceRequestDTO choiceRequestDTO) {
        ChoiceResponseDTO createdChoice = choiceService.createChoice(choiceRequestDTO);
        return ResponseEntity.ok(createdChoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChoiceResponseDTO> updateChoice(@PathVariable Long id, @Valid @RequestBody ChoiceRequestDTO updatedChoiceRequestDTO) {
        ChoiceResponseDTO updatedChoice = choiceService.updateChoice(id, updatedChoiceRequestDTO);
        return ResponseEntity.ok(updatedChoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChoice(@PathVariable Long id) {
        choiceService.deleteChoice(id);
        return ResponseEntity.status(200).build();
    }
}
