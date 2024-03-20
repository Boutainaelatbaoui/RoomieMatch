package com.example.roomiematch.service.implementations;

import com.example.roomiematch.enums.RequestStatus;
import com.example.roomiematch.mapper.RequestMapper;
import com.example.roomiematch.model.dto.request.RequestRequestDTO;
import com.example.roomiematch.model.dto.response.RequestResponseDTO;
import com.example.roomiematch.model.entities.Request;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.RequestRepository;
import com.example.roomiematch.repository.UserRepository;
import com.example.roomiematch.service.IRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements IRequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, UserRepository userRepository, RequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.requestMapper = requestMapper;
    }

    @Override
    public RequestResponseDTO sendRequest(RequestRequestDTO requestDTO) {
        if (!userRepository.existsByEmail(requestDTO.getSenderEmail())) {
            throw new IllegalArgumentException("Sender email does not exist: " + requestDTO.getSenderEmail());
        }

        if (!userRepository.existsByEmail(requestDTO.getRecipientEmail())) {
            throw new IllegalArgumentException("Recipient email does not exist: " + requestDTO.getRecipientEmail());
        }

        int senderGender = userRepository.findGenderByEmail(requestDTO.getSenderEmail());
        int recipientGender = userRepository.findGenderByEmail(requestDTO.getRecipientEmail());
        if (senderGender != recipientGender) {
            throw new IllegalArgumentException("Sender and recipient have different genders");
        }

        if (requestRepository.existsBySenderEmailAndRecipientEmail(requestDTO.getSenderEmail(), requestDTO.getRecipientEmail())) {
            throw new IllegalArgumentException("Request already exists");
        }

        requestDTO.setStatus(RequestStatus.PENDING);
        requestDTO.setCreatedAt(java.time.LocalDateTime.now());

        return requestMapper.toDTO(requestRepository.save(requestMapper.toEntity(requestDTO)));
    }

    @Override
    public RequestResponseDTO acceptRequest(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request with ID " + requestId + " does not exist."));

        if (!request.getStatus().equals(RequestStatus.PENDING)) {
            throw new IllegalArgumentException("Request with ID " + requestId + " cannot be accepted because its status is not pending.");
        }

        request.setStatus(RequestStatus.ACCEPTED);

        Request savedRequest = requestRepository.save(request);

        return requestMapper.toDTO(savedRequest);
    }

    @Override
    public RequestResponseDTO rejectRequest(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request with ID " + requestId + " does not exist."));

        if (!request.getStatus().equals(RequestStatus.PENDING)) {
            throw new IllegalArgumentException("Request with ID " + requestId + " cannot be rejected because its status is not pending.");
        }

        request.setStatus(RequestStatus.REJECTED);

        Request savedRequest = requestRepository.save(request);

        return requestMapper.toDTO(savedRequest);
    }


    @Override
    public List<RequestResponseDTO> getReceivedRequests(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + userEmail + " does not exist."));

        List<Request> receivedRequests = requestRepository.findByRecipient(user);

        return receivedRequests.stream()
                .map(requestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestResponseDTO> getSentRequests(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + userEmail + " does not exist."));

        List<Request> sentRequests = requestRepository.findBySender(user);

        return sentRequests.stream()
                .map(requestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestResponseDTO> getAllRequests() {
        List<Request> requests = requestRepository.findAll();
        return requests.stream()
                .map(requestMapper::toDTO)
                .collect(Collectors.toList());
    }
}
