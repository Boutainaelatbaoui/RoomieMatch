package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.request.RegisterRequest;
import com.example.roomiematch.model.dto.request.SearchUserRequestDTO;
import com.example.roomiematch.model.dto.response.UserResponseDTO;
import com.example.roomiematch.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        UserResponseDTO user = userService.getUserDetailsByEmail(email).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Void> updateUserDetailsByEmail(@PathVariable String email, @RequestBody RegisterRequest request) {
        userService.updateUserDetailsByEmail(email, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> searchUsersByName(@RequestParam("name") String name) {
        List<UserResponseDTO> users = userService.getUsersByName(name);
        return ResponseEntity.ok(users);
    }
}

