package com.example.roomiematch.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class UserQuestionPreferenceRequestDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Long questionId;
}
