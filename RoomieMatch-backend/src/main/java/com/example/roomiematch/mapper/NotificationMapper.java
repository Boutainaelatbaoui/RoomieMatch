package com.example.roomiematch.mapper;

import com.example.roomiematch.model.dto.request.NotificationRequestDTO;
import com.example.roomiematch.model.dto.response.NotificationResponseDTO;
import com.example.roomiematch.model.entities.Notification;
import com.example.roomiematch.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    private final UserMapper userMapper;

    @Autowired
    public NotificationMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public NotificationResponseDTO toDTO(Notification notification) {
        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .sender(userMapper.toDTO(notification.getSender()))
                .recipient(userMapper.toDTO(notification.getRecipient()))
                .message(notification.getMessage())
                .isRead(notification.isRead())
                .build();
    }

    public Notification toEntity(NotificationRequestDTO requestDTO) {
        return Notification.builder()
                .sender(User.builder().id(requestDTO.getSenderId()).build())
                .recipient(User.builder().id(requestDTO.getRecipientId()).build())
                .message(requestDTO.getMessage())
                .isRead(requestDTO.isRead())
                .build();
    }
}

