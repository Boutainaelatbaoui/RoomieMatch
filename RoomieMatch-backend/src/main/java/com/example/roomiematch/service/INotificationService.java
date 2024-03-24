package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.response.NotificationResponseDTO;
import com.example.roomiematch.model.entities.User;

import java.util.List;

public interface INotificationService {
    void sendNotification(User recipient, String message);
    List<NotificationResponseDTO> getNotificationsByRecipient();
    void markNotificationAsRead(Long id);
}
