package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.response.NotificationResponseDTO;
import com.example.roomiematch.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final INotificationService notificationService;

    @Autowired
    public NotificationController(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/recipient")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByRecipient() {
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsByRecipient();
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable("id") Long id) {
        notificationService.markNotificationAsRead(id);
        return ResponseEntity.ok().build();
    }
}
