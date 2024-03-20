package com.example.roomiematch.model.dto.request;

import com.example.roomiematch.enums.RequestStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestRequestDTO {
    private String senderEmail;
    private String recipientEmail;
    private RequestStatus status;
}

