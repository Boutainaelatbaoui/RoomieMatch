package com.example.roomiematch.model.dto.response;

import com.example.roomiematch.model.dto.request.QuestionRequestDTO;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireResponseResponseDTO {
    private Long id;
    private Long userId;
    private QuestionRequestDTO question;
    private Long choiceId;
}
