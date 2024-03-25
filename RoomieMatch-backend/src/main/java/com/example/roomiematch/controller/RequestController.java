package com.example.roomiematch.controller;

import com.example.roomiematch.enums.RequestStatus;
import com.example.roomiematch.model.dto.request.RequestRequestDTO;
import com.example.roomiematch.model.dto.response.RequestResponseDTO;
import com.example.roomiematch.service.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<RequestResponseDTO> sendRequest(@RequestBody RequestRequestDTO requestDTO) {
        return ResponseEntity.ok(requestService.sendRequest(requestDTO));
    }

    @PostMapping("/accept/{requestId}/{userEmail}")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<RequestResponseDTO> acceptRequest(@PathVariable Long requestId, @PathVariable String userEmail) {
        return ResponseEntity.ok(requestService.acceptRequest(requestId, userEmail));
    }

    @PostMapping("/reject/{requestId}/{userEmail}")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<RequestResponseDTO> rejectRequest(@PathVariable Long requestId, @PathVariable String userEmail) {
        return ResponseEntity.ok(requestService.rejectRequest(requestId, userEmail));
    }

    @GetMapping("/received/{userEmail}")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<List<RequestResponseDTO>> getReceivedRequests(@PathVariable String userEmail) {
        return ResponseEntity.ok(requestService.getReceivedRequests(userEmail));
    }

    @GetMapping("/sent/{userEmail}")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<List<RequestResponseDTO>> getSentRequests(@PathVariable String userEmail) {
        return ResponseEntity.ok(requestService.getSentRequests(userEmail));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<List<RequestResponseDTO>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/recipient/{senderEmail}/status/{status}")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<List<RequestResponseDTO>> getSenderRequestsByStatus(@PathVariable String senderEmail, @PathVariable RequestStatus status) {
        return ResponseEntity.ok(requestService.getSenderRequestsByStatus(senderEmail, status));
    }

    @GetMapping("/status")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<RequestStatus> getRequestStatusByEmails(
            @RequestParam("userEmail1") String userEmail1,
            @RequestParam("userEmail2") String userEmail2) {
        RequestStatus status = requestService.getRequestStatusByEmails(userEmail1, userEmail2);
        return ResponseEntity.ok(status);
    }

}

