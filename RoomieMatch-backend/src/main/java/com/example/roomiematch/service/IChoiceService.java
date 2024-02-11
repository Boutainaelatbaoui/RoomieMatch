package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.request.ChoiceRequestDTO;
import com.example.roomiematch.model.dto.response.ChoiceResponseDTO;

import java.util.List;

public interface IChoiceService {
    List<ChoiceResponseDTO> getAllChoices();
    ChoiceResponseDTO getChoiceById(Long id);
    ChoiceResponseDTO createChoice(ChoiceRequestDTO choiceRequestDTO);
    ChoiceResponseDTO updateChoice(Long id, ChoiceRequestDTO updatedChoiceRequestDTO);
    void deleteChoice(Long id);
}

