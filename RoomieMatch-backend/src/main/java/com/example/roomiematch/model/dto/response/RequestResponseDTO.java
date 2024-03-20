package com.example.roomiematch.model.dto.response;

import com.example.roomiematch.enums.RequestStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestResponseDTO {
    private Long id;
    private UserResponseDTO sender;
    private UserResponseDTO recipient;
    private RequestStatus status;
    private LocalDateTime createdAt;
    private String message;
}
