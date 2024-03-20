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
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.EnumSet;
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
        String senderEmail = requestDTO.getSenderEmail();
        String recipientEmail = requestDTO.getRecipientEmail();

        if (!userRepository.existsByEmail(senderEmail)) {
            throw new EntityNotFoundException("Sender email does not exist: " + senderEmail);
        }

        if (!userRepository.existsByEmail(recipientEmail)) {
            throw new EntityNotFoundException("Recipient email does not exist: " + recipientEmail);
        }

        int senderGender = userRepository.findGenderByEmail(senderEmail);
        int recipientGender = userRepository.findGenderByEmail(recipientEmail);
        if (senderGender != recipientGender) {
            throw new ValidationException("Sender and recipient have different genders");
        }

        if (requestRepository.existsBySenderEmailAndRecipientEmail(senderEmail, recipientEmail)) {
            throw new ValidationException("Request already exists");
        }

        if (requestRepository.existsBySenderEmailAndRecipientEmail(recipientEmail, senderEmail)) {
            throw new ValidationException("Request already exists with the sender as the recipient and vice versa");
        }

        requestDTO.setStatus(RequestStatus.PENDING);
        requestDTO.setCreatedAt(LocalDateTime.now());

        return requestMapper.toDTO(requestRepository.save(requestMapper.toEntity(requestDTO)));
    }


    @Override
    public RequestResponseDTO acceptRequest(Long requestId, String userEmail) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request with ID " + requestId + " does not exist."));

        if (!userEmail.equals(request.getRecipient().getEmail())) {
            throw new ValidationException("User is not authorized to accept this request.");
        }

        if (!request.getStatus().equals(RequestStatus.PENDING)) {
            throw new ValidationException("Request with ID " + requestId + " cannot be accepted because its status is not pending.");
        }

        request.setStatus(RequestStatus.ACCEPTED);

        Request savedRequest = requestRepository.save(request);

        return requestMapper.toDTO(savedRequest);
    }

    @Override
    public RequestResponseDTO rejectRequest(Long requestId, String userEmail) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request with ID " + requestId + " does not exist."));

        if (!userEmail.equals(request.getRecipient().getEmail())) {
            throw new ValidationException("User is not authorized to reject this request.");
        }

        if (!request.getStatus().equals(RequestStatus.PENDING)) {
            throw new ValidationException("Request with ID " + requestId + " cannot be rejected because its status is not pending.");
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
                .orElseThrow(() -> new EntityNotFoundException("User with email " + userEmail + " does not exist."));

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

    @Override
    public List<RequestResponseDTO> getSenderRequestsByStatus(String senderEmail, RequestStatus status) {
        userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + senderEmail + " does not exist."));

        if (!EnumSet.of(RequestStatus.PENDING, RequestStatus.ACCEPTED, RequestStatus.REJECTED).contains(status)) {
            throw new ValidationException("Invalid request status");
        }

        List<Request> requests = requestRepository.findBySenderEmailAndStatus(senderEmail, status);
        return requests.stream()
                .map(requestMapper::toDTO)
                .collect(Collectors.toList());
    }
}
