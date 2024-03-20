package com.example.roomiematch.mapper;

import com.example.roomiematch.model.dto.request.RequestRequestDTO;
import com.example.roomiematch.model.dto.response.RequestResponseDTO;
import com.example.roomiematch.model.entities.Request;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    public RequestResponseDTO toDTO(Request request) {
        return RequestResponseDTO.builder()
                .id(request.getId())
                .sender(userMapper.toDTO(request.getSender()))
                .recipient(userMapper.toDTO(request.getRecipient()))
                .status(request.getStatus())
                .build();
    }

    public Request toEntity(RequestRequestDTO requestDTO) {
        User sender = userRepository.findByEmail(requestDTO.getSenderEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + requestDTO.getSenderEmail()));

        User recipient = userRepository.findByEmail(requestDTO.getRecipientEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + requestDTO.getRecipientEmail()));

        return Request.builder()
                .sender(sender)
                .recipient(recipient)
                .status(requestDTO.getStatus())
                .build();
    }
}
