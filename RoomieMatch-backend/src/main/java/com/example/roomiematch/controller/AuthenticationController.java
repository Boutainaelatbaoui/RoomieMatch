package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.request.AuthenticationRequest;
import com.example.roomiematch.model.dto.request.RefreshTokenRequest;
import com.example.roomiematch.model.dto.request.RegisterRequest;
import com.example.roomiematch.model.dto.response.AuthenticationResponse;
import com.example.roomiematch.model.dto.response.RefreshTokenResponse;
import com.example.roomiematch.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest, HttpServletResponse response) {
        try {
            RefreshTokenResponse authResponse = authenticationService.refreshToken(refreshTokenRequest, response);
            return ResponseEntity.ok(authResponse);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}