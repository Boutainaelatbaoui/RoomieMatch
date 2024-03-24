package com.example.roomiematch.service.implementations;

import com.example.roomiematch.enums.RequestStatus;
import com.example.roomiematch.mapper.RequestMapper;
import com.example.roomiematch.model.dto.request.RequestRequestDTO;
import com.example.roomiematch.model.dto.response.RequestResponseDTO;
import com.example.roomiematch.model.entities.Request;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.RequestRepository;
import com.example.roomiematch.repository.UserRepository;
import com.example.roomiematch.service.INotificationService;
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
    private final INotificationService notificationService;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, UserRepository userRepository, RequestMapper requestMapper, INotificationService notificationService) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.requestMapper = requestMapper;
        this.notificationService = notificationService;
    }

    @Override
    public RequestResponseDTO sendRequest(RequestRequestDTO requestDTO) {
        String senderEmail = requestDTO.getSenderEmail();
        String recipientEmail = requestDTO.getRecipientEmail();

        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new EntityNotFoundException("Sender email does not exist: " + senderEmail));

        User recipient = userRepository.findByEmail(recipientEmail)
                .orElseThrow(() -> new EntityNotFoundException("Recipient email does not exist: " + recipientEmail));

        int senderGender = sender.getGender();
        int recipientGender = recipient.getGender();
        if (senderGender != recipientGender) {
            throw new ValidationException("Sender and recipient have different genders");
        }

        if (requestRepository.existsBySenderEmailAndRecipientEmail(senderEmail, recipientEmail) ||
                requestRepository.existsBySenderEmailAndRecipientEmail(recipientEmail, senderEmail)) {
            throw new ValidationException("Request already exists between sender and recipient");
        }

        requestDTO.setStatus(RequestStatus.PENDING);
        requestDTO.setCreatedAt(LocalDateTime.now());

        Request savedRequest = requestRepository.save(requestMapper.toEntity(requestDTO));

        String message = "You have received a new match request from " + sender.getFirstName() + " " + sender.getLastName();
        notificationService.sendNotification(recipient, message);

        return requestMapper.toDTO(savedRequest);
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

        User sender = request.getSender();
        User recipient = request.getRecipient();
        String message = "Your match request has been accepted by " + recipient.getFirstName() + " " + recipient.getLastName();
        notificationService.sendNotification(sender, message);

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

    @Override
    public RequestStatus getRequestStatusByEmails(String userEmail1, String userEmail2) {
        Request request = requestRepository.findBySenderEmailAndRecipientEmail(userEmail1, userEmail2)
                .orElseGet(() -> requestRepository.findBySenderEmailAndRecipientEmail(userEmail2, userEmail1)
                        .orElse(null));
        return request != null ? request.getStatus() : RequestStatus.PENDING; // Assuming default status is PENDING
    }
}
