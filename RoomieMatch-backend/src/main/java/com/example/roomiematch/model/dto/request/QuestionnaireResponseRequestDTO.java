package com.example.roomiematch.model.dto.request;

import lombok.*;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireResponseRequestDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Long questionId;

    @NotNull
    private Long choiceId;
}
