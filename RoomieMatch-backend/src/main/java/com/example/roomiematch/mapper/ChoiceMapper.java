package com.example.roomiematch.mapper;

import com.example.roomiematch.model.dto.request.ChoiceRequestDTO;
import com.example.roomiematch.model.dto.response.ChoiceResponseDTO;
import com.example.roomiematch.model.entities.Choice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChoiceMapper {

    public ChoiceResponseDTO toDTO(Choice choice) {
        return ChoiceResponseDTO.builder()
                .id(choice.getId())
                .choiceText(choice.getChoiceText())
                .build();
    }

    public List<ChoiceResponseDTO> toDTOList(List<Choice> choices) {
        return choices.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Choice toEntity(ChoiceRequestDTO choiceRequestDTO) {
        return Choice.builder()
                .choiceText(choiceRequestDTO.getChoiceText())
                .build();
    }
}
