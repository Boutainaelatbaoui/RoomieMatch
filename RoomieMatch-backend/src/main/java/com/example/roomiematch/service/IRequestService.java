package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.request.RequestRequestDTO;
import com.example.roomiematch.model.dto.response.RequestResponseDTO;

import java.util.List;

public interface IRequestService {
    RequestResponseDTO sendRequest(RequestRequestDTO requestDTO);
    RequestResponseDTO acceptRequest(Long requestId, String userEmail);
    RequestResponseDTO rejectRequest(Long requestId, String userEmail);
    List<RequestResponseDTO> getReceivedRequests(String userEmail);
    List<RequestResponseDTO> getSentRequests(String userEmail);
    List<RequestResponseDTO> getAllRequests();
}

