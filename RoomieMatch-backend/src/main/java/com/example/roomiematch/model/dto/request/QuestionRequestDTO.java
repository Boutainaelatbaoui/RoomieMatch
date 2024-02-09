package com.example.roomiematch.model.dto.request;

import lombok.*;

import jakarta.validation.constraints.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDTO {
    @NotBlank(message = "Question text cannot be blank")
    @Size(max = 255, message = "Question text must be at most 255 characters")
    private String questionText;
}
