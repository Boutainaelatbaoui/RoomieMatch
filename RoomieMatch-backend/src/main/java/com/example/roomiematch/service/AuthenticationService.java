package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.request.AuthenticationRequest;
import com.example.roomiematch.model.dto.request.RefreshTokenRequest;
import com.example.roomiematch.model.dto.request.RegisterRequest;
import com.example.roomiematch.model.dto.response.AuthenticationResponse;
import com.example.roomiematch.model.dto.response.RefreshTokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse register(RegisterRequest request);
    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest, HttpServletResponse response) throws IOException;
}
