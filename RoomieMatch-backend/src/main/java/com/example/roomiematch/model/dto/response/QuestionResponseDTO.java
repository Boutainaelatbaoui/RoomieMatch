package com.example.roomiematch.model.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDTO {
    private Long id;
    private String questionText;
    private List<ChoiceResponseDTO> choices;
}
