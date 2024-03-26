package com.example.roomiematch.service;

import com.example.roomiematch.enums.RequestStatus;
import com.example.roomiematch.model.dto.request.RequestRequestDTO;
import com.example.roomiematch.model.dto.response.RequestResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IRequestService {
    RequestResponseDTO sendRequest(RequestRequestDTO requestDTO);
    RequestResponseDTO acceptRequest(Long requestId, String userEmail);
    RequestResponseDTO rejectRequest(Long requestId, String userEmail);
    List<RequestResponseDTO> getReceivedRequests(String userEmail);
    List<RequestResponseDTO> getSentRequests(String userEmail);
    List<RequestResponseDTO> getAllRequests();
    List<RequestResponseDTO> getSenderRequestsByStatus(String recipientEmail, RequestStatus status);
    Optional<RequestStatus> getRequestStatusByEmails(String userEmail1, String userEmail2);
}

