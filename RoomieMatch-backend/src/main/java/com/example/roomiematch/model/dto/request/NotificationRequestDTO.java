package com.example.roomiematch.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {
    private Long senderId;
    private Long recipientId;
    private String message;
    private boolean isRead;
}

