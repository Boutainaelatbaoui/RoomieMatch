package com.example.roomiematch.model.dto.response;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestionPreferenceResponseDTO {
    private Long id;
    private Long userId;
    private Long questionId;
}

