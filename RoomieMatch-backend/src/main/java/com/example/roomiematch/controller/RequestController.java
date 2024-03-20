package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.request.RequestRequestDTO;
import com.example.roomiematch.model.dto.response.RequestResponseDTO;
import com.example.roomiematch.service.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/requests")
public class RequestController {

    private final IRequestService requestService;

    @Autowired
    public RequestController(IRequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/send")
    public ResponseEntity<RequestResponseDTO> sendRequest(@RequestBody RequestRequestDTO requestDTO) {
        return ResponseEntity.ok(requestService.sendRequest(requestDTO));
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<RequestResponseDTO> acceptRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.acceptRequest(requestId));
    }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<RequestResponseDTO> rejectRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.rejectRequest(requestId));
    }

    @GetMapping("/received/{userEmail}")
    public ResponseEntity<List<RequestResponseDTO>> getReceivedRequests(@PathVariable String userEmail) {
        return ResponseEntity.ok(requestService.getReceivedRequests(userEmail));
    }

    @GetMapping("/sent/{userEmail}")
    public ResponseEntity<List<RequestResponseDTO>> getSentRequests(@PathVariable String userEmail) {
        return ResponseEntity.ok(requestService.getSentRequests(userEmail));
    }

    @GetMapping
    public ResponseEntity<List<RequestResponseDTO>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

}

