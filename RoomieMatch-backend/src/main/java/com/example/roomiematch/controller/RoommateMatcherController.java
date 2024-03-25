package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.response.UserResponseDTO;
import com.example.roomiematch.service.IRoommateMatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roommate-matches")
public class RoommateMatcherController {

    private final IRoommateMatcherService roommateMatcherService;

    @Autowired
    public RoommateMatcherController(IRoommateMatcherService roommateMatcherService) {
        this.roommateMatcherService = roommateMatcherService;
    }

    @GetMapping("/user/{userEmail}")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public List<UserResponseDTO> findRoommatesForUser(@PathVariable String userEmail) {
        return roommateMatcherService.findRoommatesForUserByEmail(userEmail);
    }
}

