package com.example.roomiematch.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceRequestDTO {
    @NotBlank
    private String choiceText;

    @NotNull
    private Long questionId;
}
