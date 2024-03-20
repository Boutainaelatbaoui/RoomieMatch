package com.example.roomiematch.model.dto.request;

import com.example.roomiematch.enums.RequestStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestRequestDTO {
    @NotBlank(message = "Sender email cannot be blank")
    private String senderEmail;
    @NotBlank(message = "Recipient email cannot be blank")
    private String recipientEmail;
    private RequestStatus status;
    private LocalDateTime createdAt;
    @Size(min = 2, max = 255, message = "Message must be between 2 and 255 characters")
    private String message;
}

