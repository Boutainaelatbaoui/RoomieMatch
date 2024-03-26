package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.request.RegisterRequest;
import com.example.roomiematch.model.dto.request.SearchUserRequestDTO;
import com.example.roomiematch.model.dto.response.UserResponseDTO;
import com.example.roomiematch.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roomates")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        UserResponseDTO user = userService.getUserDetailsByEmail(email).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{email}")
    @PreAuthorize("hasAnyAuthority('CAN_UPDATE_PROFILE')")
    public ResponseEntity<Void> updateUserDetailsByEmail(@PathVariable String email, @RequestBody RegisterRequest request) {
        userService.updateUserDetailsByEmail(email, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<List<UserResponseDTO>> searchUsersByName(@RequestParam("name") String name) {
        List<UserResponseDTO> users = userService.getUsersByName(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/accepted")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<List<UserResponseDTO>> getUsersWithAcceptedRequests() {
        return ResponseEntity.ok(userService.getUsersWithAcceptedRequests());
    }
}

