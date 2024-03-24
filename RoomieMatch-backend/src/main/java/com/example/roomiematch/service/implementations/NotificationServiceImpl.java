package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.NotificationMapper;
import com.example.roomiematch.model.dto.response.NotificationResponseDTO;
import com.example.roomiematch.model.entities.Notification;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.NotificationRepository;
import com.example.roomiematch.repository.UserRepository;
import com.example.roomiematch.service.INotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements INotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapper notificationMapper, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void sendNotification(User recipient, String message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderEmail = authentication.getName();

        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new EntityNotFoundException("Sender not found with email: " + senderEmail));

        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notification.setMessage(message);

        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByRecipient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User recipient = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));

        List<Notification> notifications = notificationRepository.findByRecipient(recipient);
        return notifications.stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
    }
}

