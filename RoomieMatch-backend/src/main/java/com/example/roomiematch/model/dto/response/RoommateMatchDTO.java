package com.example.roomiematch.model.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoommateMatchDTO {
    private UserResponseDTO user;
    private int matchScore;
    private double percentageMatch;
}

