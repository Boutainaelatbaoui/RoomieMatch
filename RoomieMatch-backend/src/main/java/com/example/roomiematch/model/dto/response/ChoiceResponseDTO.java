package com.example.roomiematch.model.dto.response;

import com.example.roomiematch.model.dto.request.QuestionRequestDTO;
import com.example.roomiematch.model.entities.Question;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceResponseDTO {
    private Long id;
    private String choiceText;
    private QuestionRequestDTO question;
}

